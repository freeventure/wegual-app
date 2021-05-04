package com.wegual.giveupservice.es.actions;


import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wegual.giveupservice.ElasticSearchConfig;
import com.wegual.giveupservice.message.GiveUpLikeTimelineItemBuilder;
import com.wegual.giveupservice.message.TimelineMessageSender;

import app.wegual.common.asynch.SenderRunnable;
import app.wegual.common.model.GenericItem;
import app.wegual.common.model.GiveUp;
import app.wegual.common.model.GiveUpLike;
import app.wegual.common.model.GiveUpTimelineItem;
import app.wegual.common.model.User;
import app.wegual.common.model.UserActionType;
import app.wegual.common.model.UserTimelineItem;
import app.wegual.common.service.UserUtils;
import app.wegual.common.util.ESIndices;
import app.wegual.common.model.UserActionItem;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GiveUpLikeService {

	@Autowired
	private ElasticSearchConfig esConfig;
	
	@Autowired
	@Qualifier("executorMessageSender")
	TaskExecutor te;

	@Autowired
	TimelineMessageSender utms;
	
	public void likeGiveUp(UserActionItem uat) {
		String userId = uat.getActorId();
		String giveupId = uat.getTargetId();
		
		GenericItem<String> user = new GenericItem<String>();
		GenericItem<String> giveup = new GenericItem<String>();
		
		user = getUserGenericItem(userId);
		giveup = getGiveUpGenericItem(giveupId);

		GiveUpLike gul = new GiveUpLike();
		String id = giveup.getId() + "_" + user.getId();
		gul.setId(id);
		gul.setUser(user);
		gul.setGiveup(giveup);
		gul.setLikeDate(System.currentTimeMillis());
		
		
		RestHighLevelClient client = esConfig.getElasticsearchClient();
		try {
			IndexRequest indexRequest = new IndexRequest(ESIndices.GIVEUP_LIKE_INDEX).id(gul.getId()).source(new ObjectMapper().writeValueAsString(gul), XContentType.JSON);
			indexRequest.opType(DocWriteRequest.OpType.INDEX);
			IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
			if ( (indexResponse.getResult() != DocWriteResponse.Result.CREATED) && (indexResponse.getResult() != DocWriteResponse.Result.UPDATED) )
				throw new IllegalStateException("GiveUp was not liked for id: " + userId);
			else if(indexResponse.getResult() == DocWriteResponse.Result.UPDATED)
			{
				log.info("Giveup " + giveupId + " was already liked successfully for userId: " + userId);
				return;
			}
			{	
				log.info("Giveup " + giveupId + " liked successfully for uderId: " + userId);
				UserTimelineItem uti = new GiveUpLikeTimelineItemBuilder(UserActionType.LIKE)
						.time(System.currentTimeMillis())
						.user(user)
						.giveup(giveup)
						.build();
				te.execute(new SenderRunnable<TimelineMessageSender, UserTimelineItem>(utms, uti));
				GiveUpTimelineItem gti = new GiveUpLikeTimelineItemBuilder(UserActionType.UNLIKE)
						.time(System.currentTimeMillis())
						.user(user)
						.giveup(giveup)
						.buildForGiveUp(giveupId);
				te.execute(new SenderRunnable<TimelineMessageSender, GiveUpTimelineItem>(utms, gti));
			}
		} catch (Exception e) {
			log.error("Unable to like a giveup for given userId in ES", giveup.getName(), user.getId(), e);
		}
	}
	
	public void unlikeGiveUp(UserActionItem gut) {
		String id = gut.getTargetId() + "_" + gut.getActorId();
		
		DeleteByQueryRequest request = new DeleteByQueryRequest(ESIndices.GIVEUP_LIKE_INDEX); 
		
		TermQueryBuilder termQuery = QueryBuilders.termQuery("id", id);
				
		request.setQuery(termQuery);
		request.setMaxDocs(1);
		request.setBatchSize(1);
		request.setRefresh(true);
		
		try {
			BulkByScrollResponse bulkResponse = esConfig.getElasticsearchClient().deleteByQuery(request, RequestOptions.DEFAULT);
			log.info("bulk response for delete: " + bulkResponse.getDeleted());
			if(bulkResponse.getDeleted() != 1)
				throw new IllegalStateException("exactly one document was not deleted");
			else {
				log.info("Giveup " + gut.getTargetId() + " unliked successfully for userId: " + gut.getActorId());
				GenericItem<String> user = new GenericItem<String>();
				GenericItem<String> giveup = new GenericItem<String>();
				
				user = getUserGenericItem(gut.getActorId());
				giveup = getGiveUpGenericItem(gut.getTargetId());
				UserTimelineItem uti = new GiveUpLikeTimelineItemBuilder(UserActionType.UNLIKE)
						.time(System.currentTimeMillis())
						.user(user)
						.giveup(giveup)
						.build();
				te.execute(new SenderRunnable<TimelineMessageSender, UserTimelineItem>(utms, uti));
				GiveUpTimelineItem gti = new GiveUpLikeTimelineItemBuilder(UserActionType.UNLIKE)
						.time(System.currentTimeMillis())
						.user(user)
						.giveup(giveup)
						.buildForGiveUp(gut.getTargetId());
				te.execute(new SenderRunnable<TimelineMessageSender, GiveUpTimelineItem>(utms, gti));
			}
		} catch (IOException e) {
			
			log.error("Unable to unlike a giveup document by query: " + id, e);
		}
	}
	
	public GenericItem<String> getUserGenericItem(String userId) {
		GenericItem<String> user = new GenericItem<String>();
		try {
			SearchRequest searchRequest = new SearchRequest("user_idx");
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
			sourceBuilder.query(QueryBuilders.termQuery("user_id", userId));
			searchRequest.source(sourceBuilder);
			
			RestHighLevelClient client = esConfig.getElasticsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			
			User u = null;
			if(searchResponse.getHits().getTotalHits().value>0L) {
				Map<String, Object> src = null;
				for(SearchHit hit: searchResponse.getHits()) {
					src = hit.getSourceAsMap();
					u = UserUtils.userFromESDocument(src);
				}
				user.setId(u.getId());
				String name = u.getFirstName() + " " + u.getLastName();
				user.setName(name);
				user.setPictureLink(u.getPictureLink());
				String permalink = "/user/" + u.getId();
				user.setPermalink(permalink);
				return user;
			}
			
		} catch (Exception e) {
			log.info("User not found", e);
		}
		return user;
	}
	
	public GenericItem<String> getGiveUpGenericItem(String giveupId) {
		GenericItem<String> giveUp = new GenericItem<String>();
		try {
			SearchRequest searchRequest = new SearchRequest("giveup_idx");
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
			sourceBuilder.query(QueryBuilders.termQuery("giveup_id", giveupId));
			searchRequest.source(sourceBuilder);
			
			RestHighLevelClient client = esConfig.getElasticsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			
			GiveUp g = null;
			if(searchResponse.getHits().getTotalHits().value>0L) {
				for(SearchHit hit : searchResponse.getHits()) {
					g = new ObjectMapper().readValue(hit.getSourceAsString(), GiveUp.class);
				}
			}
			giveUp.setId(g.getId());
			giveUp.setName(g.getName());
			giveUp.setPictureLink(g.getPictureLink());
			String permaLink = "/giveup/" + g.getId();
			giveUp.setPermalink(permaLink);
			return giveUp;
			
		} catch (Exception e) {
			log.info("GiveUp not found", e);
		}
		return giveUp;
	}
}
