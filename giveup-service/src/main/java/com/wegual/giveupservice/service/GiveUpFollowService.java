package com.wegual.giveupservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.search.join.ScoreMode;
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
import com.wegual.giveupservice.GiveUpUtils;
import com.wegual.giveupservice.message.GiveUpLikeTimelineItemBuilder;
import com.wegual.giveupservice.message.TimelineMessageSender;

import app.wegual.common.asynch.SenderRunnable;
import app.wegual.common.model.GenericItem;
import app.wegual.common.model.GiveUp;
import app.wegual.common.model.GiveUpFollow;
import app.wegual.common.model.GiveUpLike;
import app.wegual.common.model.GiveUpTimelineItem;
import app.wegual.common.model.UserActionItem;
import app.wegual.common.model.UserActionType;
import app.wegual.common.model.UserTimelineItem;
import app.wegual.common.util.ESIndices;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GiveUpFollowService {

	@Autowired
	private ElasticSearchConfig esConfig;

	@Autowired
	private GiveUpUtils gu;
	
	@Autowired
	@Qualifier("executorMessageSender")
	TaskExecutor te;
	
	@Autowired
	private TimelineMessageSender tms;

	public void followGiveUp(UserActionItem uai) {
		String userId = uai.getActorId();
		String giveupId = uai.getTargetId();

		GenericItem<String> user = gu.getUserGenericItem(userId);
		GenericItem<String> giveup = gu.getGiveUpGenericItem(giveupId);

		GiveUpFollow guf = new GiveUpFollow();
		String id = giveup.getId() + "_" + user.getId();
		guf.setId(id);
		guf.setUserFollower(user);
		guf.setGiveupFollowee(giveup);
		guf.setFollowDate(System.currentTimeMillis());

		RestHighLevelClient client = esConfig.getElasticsearchClient();
		try {
			IndexRequest indexRequest = new IndexRequest(ESIndices.GIVEUP_FOLLOW_INDEX).id(guf.getId()).source(new ObjectMapper().writeValueAsString(guf), XContentType.JSON);
			indexRequest.opType(DocWriteRequest.OpType.INDEX);
			IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
			if ( (indexResponse.getResult() != DocWriteResponse.Result.CREATED) && (indexResponse.getResult() != DocWriteResponse.Result.UPDATED) )
				throw new IllegalStateException("GiveUp was not followed for userid: " + userId);
			else if(indexResponse.getResult() == DocWriteResponse.Result.UPDATED)
			{
				log.info("Giveup " + giveupId + " was already followed successfully for userId: " + userId);
				return;
			}
			{	
				log.info("Giveup " + giveupId + " followed successfully for userId: " + userId);
				UserTimelineItem uti = new GiveUpLikeTimelineItemBuilder(UserActionType.FOLLOW_GIVEUP)
						.time(System.currentTimeMillis())
						.user(user)
						.giveup(giveup)
						.build();
				te.execute(new SenderRunnable<TimelineMessageSender, UserTimelineItem>(tms, uti));
				GiveUpTimelineItem gti = new GiveUpLikeTimelineItemBuilder(UserActionType.FOLLOW_GIVEUP)
						.time(System.currentTimeMillis())
						.user(user)
						.giveup(giveup)
						.buildForGiveUp(giveupId);
				te.execute(new SenderRunnable<TimelineMessageSender, GiveUpTimelineItem>(tms, gti));
			}
		} catch (Exception e) {
			log.error("Unable to follow giveup for given userId in ES", giveup.getName(), user.getId(), e);
		}
	}

	public void unfollowGiveup(UserActionItem uai) {
		String id = uai.getTargetId() + "_" + uai.getActorId();

		DeleteByQueryRequest request = new DeleteByQueryRequest(ESIndices.GIVEUP_FOLLOW_INDEX); 

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
				log.info("Giveup " + uai.getTargetId() + " unfollowed successfully for userId: " + uai.getActorId());
				GenericItem<String> user = new GenericItem<String>();
				GenericItem<String> giveup = new GenericItem<String>();

				user = gu.getUserGenericItem(uai.getActorId());
				giveup = gu.getGiveUpGenericItem(uai.getTargetId());
				UserTimelineItem uti = new GiveUpLikeTimelineItemBuilder(UserActionType.UNFOLLOW_GIVEUP)
						.time(System.currentTimeMillis())
						.user(user)
						.giveup(giveup)
						.build();
				te.execute(new SenderRunnable<TimelineMessageSender, UserTimelineItem>(tms, uti));
				GiveUpTimelineItem gti = new GiveUpLikeTimelineItemBuilder(UserActionType.UNFOLLOW_GIVEUP)
						.time(System.currentTimeMillis())
						.user(user)
						.giveup(giveup)
						.buildForGiveUp(uai.getTargetId());
				te.execute(new SenderRunnable<TimelineMessageSender, GiveUpTimelineItem>(tms, gti));
			}
		} catch (IOException e) {

			log.error("Unable to unfollow a giveup document by query: " + id, e);
		}
	}
	
	public List<GiveUp> suggestGiveUpToFollow(String userId) {
		log.info("Searching for all giveup suggestion for :" + userId);
		List<GiveUp> giveups = new ArrayList<GiveUp>();

		RestHighLevelClient client = esConfig.getElasticsearchClient();

		SearchRequest searchRequest = new SearchRequest(ESIndices.GIVEUP_FOLLOW_INDEX);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.nestedQuery("user_follower",QueryBuilders.boolQuery().must(QueryBuilders.termQuery("user_follower.id", userId)), ScoreMode.None));
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse;
		
		SearchRequest giveupRequest = new SearchRequest(ESIndices.GIVEUP_INDEX);
		SearchSourceBuilder giveupBuilder = new SearchSourceBuilder();
		giveupBuilder.query(QueryBuilders.matchAllQuery());
		giveupRequest.source(giveupBuilder);
		SearchResponse giveupResponse;
		
		try {
			List<String> giveupFollowedId = new ArrayList<String>();
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			GiveUpFollow guf = null;
			for(SearchHit hit : searchResponse.getHits()) {
				guf = new ObjectMapper().readValue(hit.getSourceAsString(), GiveUpFollow.class);
				giveupFollowedId.add(guf.getGiveupFollowee().getId());
			}
			
			giveupResponse = client.search(giveupRequest, RequestOptions.DEFAULT);
			GiveUp gu = null;
			for(SearchHit hit: giveupResponse.getHits()) {
				gu = new ObjectMapper().readValue(hit.getSourceAsString(), GiveUp.class);
				if(!giveupFollowedId.contains(gu.getId())) {
					giveups.add(gu);
				}
			}
		} catch (IOException e) {
			log.info("Failed to fetch suggestGiveUpToFollow", e);
		}
		return giveups;

	}
	
	public List<GenericItem<String>> allGiveUpFollowedByUser(String userId){
		log.info("Searching for all giveups followed by :" + userId);
		List<GenericItem<String>> giveups = new ArrayList<GenericItem<String>>();
		
		SearchRequest searchRequest = new SearchRequest(ESIndices.GIVEUP_FOLLOW_INDEX);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.nestedQuery("user_follower",QueryBuilders.boolQuery().must(QueryBuilders.termQuery("user_follower.id", userId)), ScoreMode.None));
		searchRequest.source(sourceBuilder);
		
		RestHighLevelClient client = esConfig.getElasticsearchClient();
		
		try {
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			log.info("No. of giveups followed by user : " + searchResponse.getHits().getTotalHits());
			for(SearchHit hit : searchResponse.getHits()) {
				GiveUpFollow guf = new ObjectMapper().readValue(hit.getSourceAsString(), GiveUpFollow.class);
				giveups.add(guf.getGiveupFollowee());
			}
		} catch (Exception e) {
			log.info("Failed to fetch giveups liked by user", e);
		}
		return giveups;
	}

}
