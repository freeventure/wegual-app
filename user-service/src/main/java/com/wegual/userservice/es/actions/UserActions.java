package com.wegual.userservice.es.actions;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wegual.userservice.ElasticSearchConfig;

import app.wegual.common.model.FeedLike;
import app.wegual.common.model.FeedView;
import app.wegual.common.model.UserActionItem;
import app.wegual.common.model.UserTimelineItem;
import app.wegual.common.util.ESIndices;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserActions {
	
	private static String USER_INDEX = "user_idx";
	private static String USER_FEED_LIKE_ACTION_INDEX = "user_feed_like_action_idx";
	private static String USER_FEED_VIEW_ACTION_INDEX = "user_feed_view_action_idx";
	@Autowired
	private ElasticSearchConfig esConfig;
	
	@Autowired
	@Qualifier("executorESActions")
	TaskExecutor te;
	
	protected void processAsynch(ESUserAction action, UserTimelineItem uti) {
		te.execute(new ESAsyncCommandRunner(action, uti));
	}
	
	public void userUpdateProfileImage(UserTimelineItem uti) {
		
		ESUserAction action = this::asynchUserProfileImageUpdate;
		processAsynch(action, uti);
		
	}
	
	protected void asynchUserProfileImageUpdate(UserTimelineItem uti) {
//		log.info("Received message to update image: " + uti.getActionObject().getId());
//		log.info("Permalink image: " + uti.getActionObject().getPermalink());
//		log.info("Action Summary: " + uti.getActionObject().getSummary());
		try {
			XContentBuilder builder = XContentFactory.jsonBuilder();
			builder.startObject();
			{
				builder.field("picture_link", uti.getActionObject().getPermalink());
			}
			builder.endObject();
			UpdateRequest request = new UpdateRequest(USER_INDEX, uti.getTarget().getId()).doc(builder);
			UpdateResponse updateResponse = esConfig.getElastcsearchClient().update(request, RequestOptions.DEFAULT);
			if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
			    log.info("User document updated for picture_link: " + uti.getActionObject().getPermalink());
			} else  {
				log.error("Expected document update but got: " + updateResponse.getResult());
			}
		} catch (Exception e) {
			log.error("User document update operation failed.", e);
		}
	}

	public void likePost(UserActionItem uat) {
		String id = uat.getActorId()+"_"+uat.getTargetId();
		FeedLike fdl = new FeedLike(id, uat.getActorId(), uat.getTargetId(), System.currentTimeMillis());
		
		try {
			IndexRequest indexRequest = new IndexRequest(USER_FEED_LIKE_ACTION_INDEX)
					.id(id)
					.source(new ObjectMapper().writeValueAsString(fdl), XContentType.JSON);
			indexRequest.opType(DocWriteRequest.OpType.INDEX);
			IndexResponse indexResponse = esConfig.getElastcsearchClient().index(indexRequest, RequestOptions.DEFAULT);
			if ( (indexResponse.getResult() != DocWriteResponse.Result.CREATED) && (indexResponse.getResult() != DocWriteResponse.Result.UPDATED))
				throw new IllegalStateException("Post was not liked for id: " + uat.getTargetId());
			else if(indexResponse.getResult() == DocWriteResponse.Result.UPDATED)
			{
				log.info("Post " + uat.getTargetId() + " was already liked successfully for userId: " + uat.getActorId());
				return;
			}
			else {
				log.info("Post " + uat.getTargetId() + " liked successfully for userId: " + uat.getActorId());
				
				UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest("user_feed_idx");
				updateByQueryRequest.setQuery(new TermQueryBuilder("_id", uat.getTargetId()));
				
				Script script = new Script(ScriptType.INLINE, "painless", "ctx._source.detail_actions.likes++", Collections.EMPTY_MAP);
				updateByQueryRequest.setScript(script);
				
				BulkByScrollResponse bulkResponse = esConfig.getElastcsearchClient().updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
				
			}
		} catch (Exception e) {
			log.error("Unable to like a post for given userId in ES",  uat.getActorId(), e);
		}
		
	}
	public void unlikePost(UserActionItem uat){
		String id = uat.getActorId()+"_"+uat.getTargetId();
		DeleteByQueryRequest request = new DeleteByQueryRequest(USER_FEED_LIKE_ACTION_INDEX);
		TermQueryBuilder termQuery = QueryBuilders.termQuery("id", id);
		request.setQuery(termQuery);
		request.setMaxDocs(1);
		request.setBatchSize(1);
		request.setRefresh(true);
		
		try {
			BulkByScrollResponse bulkResponse = esConfig.getElastcsearchClient().deleteByQuery(request, RequestOptions.DEFAULT);
			log.info("bulk response for delete: " + bulkResponse.getDeleted());
			if(bulkResponse.getDeleted() != 1)
				throw new IllegalStateException("exactly one document was not deleted");
			else {
				log.info("Post " + uat.getTargetId() + " unliked successfully for userId: " + uat.getActorId());
				
				UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest("user_feed_idx");
				updateByQueryRequest.setQuery(new TermQueryBuilder("_id", uat.getTargetId()));
				
				Script script = new Script(ScriptType.INLINE, "painless", "ctx._source.detail_actions.likes--", Collections.EMPTY_MAP);
				updateByQueryRequest.setScript(script);
				
				BulkByScrollResponse response = esConfig.getElastcsearchClient().updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
			}
		} catch (IOException e) {
			log.error("Unable to unlike a giveup document by query: " + id, e);
			e.printStackTrace();
		}

	}

	public void viewPost(UserActionItem uat) {
		String id = uat.getActorId()+"_"+uat.getTargetId();
		FeedView fv = new FeedView(id, uat.getActorId(), uat.getTargetId(), System.currentTimeMillis());
		try {
			IndexRequest indexRequest = new IndexRequest(USER_FEED_VIEW_ACTION_INDEX)
					.id(id)
					.source(new ObjectMapper().writeValueAsString(fv), XContentType.JSON);
			indexRequest.opType(DocWriteRequest.OpType.INDEX);
			IndexResponse indexResponse = esConfig.getElastcsearchClient().index(indexRequest, RequestOptions.DEFAULT);
			if ( (indexResponse.getResult() != DocWriteResponse.Result.CREATED) && (indexResponse.getResult() != DocWriteResponse.Result.UPDATED))
				throw new IllegalStateException("Post was not viewed for id: " + uat.getTargetId());
			else if(indexResponse.getResult() == DocWriteResponse.Result.UPDATED)
			{
				log.info("Post " + uat.getTargetId() + " was already viewed successfully for userId: " + uat.getActorId());
				return;
			}
			else {
				log.info("Post " + uat.getTargetId() + " liked successfully for userId: " + uat.getActorId());
			}
		}
		catch(Exception e) {
			log.error("Unable to perform post view action for given userId in ES",  uat.getActorId(), e);
		}
	}
}
