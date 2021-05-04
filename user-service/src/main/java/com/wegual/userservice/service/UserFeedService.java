package com.wegual.userservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

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
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wegual.userservice.ElasticSearchConfig;

import app.wegual.common.model.FeedComment;
import app.wegual.common.model.FeedItemDetailActions;
import app.wegual.common.model.GenericActionTarget;
import app.wegual.common.model.GenericItem;
import app.wegual.common.model.TimelineItemDetailActions;
import app.wegual.common.model.UserActionTargetType;
import app.wegual.common.model.PledgeFeedItem;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserFeedService {

	@Autowired
	private ElasticSearchConfig esConfig;


	public List<PledgeFeedItem> getFeed(String id)
	{
		List<String> beneficiaryId = getBeneficiaryId(id);
		List<String> userId = getUserId(id);

		List<String> queryList = new ArrayList<String>();
		queryList.addAll(beneficiaryId);
		queryList.addAll(userId);
		queryList.add(id);
		RestHighLevelClient client = esConfig.getElastcsearchClient();
		List<PledgeFeedItem> feed = new ArrayList<PledgeFeedItem>();
		
		List<PledgeFeedItem> ls = new ArrayList<PledgeFeedItem>();
		Queue<PledgeFeedItem> qfeed = new LinkedList<PledgeFeedItem>();
		
		for(String query : queryList) {
			SearchRequest searchRequest = new SearchRequest("user_feed_idx");
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
			sourceBuilder.query(QueryBuilders.nestedQuery("actor",QueryBuilders.boolQuery().must(QueryBuilders.termQuery("actor.id", query)), ScoreMode.None)).sort(SortBuilders.fieldSort("date_stamp").order(SortOrder.DESC));
			searchRequest.source(sourceBuilder);

			try {
				SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
				PledgeFeedItem ufi = null;
				Map<String, Object> src = null;
				Map<String, Object> action = null;
				Map<String, Object> target = null;
				Map<String, Object> actor = null;
				Map<String, Object> detailActions = null;
				for (SearchHit hit: searchResponse.getHits()) {
					src = hit.getSourceAsMap();
					action = (Map<String, Object>)src.get("action_object");
					target = (Map<String, Object>)src.get("target_object");
					actor = (Map<String, Object>)src.get("actor");
					detailActions = (Map<String, Object>)src.get("detail_actions");
					Object detail = src.get("detail");
					Object actionDate = src.get("date_stamp");
					Object feedId = src.get("id");
					ufi = new PledgeFeedItem(parseActor(actor), (String) detail, parseDetailActions(detailActions), parseActionTarget(action));
					ufi.setTarget(parseActionTarget(target));
					ufi.setId((String)feedId);
					if(actionDate != null && !actionDate.toString().isEmpty())
						ufi.setActionDate(Long.valueOf(actionDate.toString()));
					feed.add(ufi);
					qfeed.add(ufi);
				}
				List<String> viewedPosts = getViewedPost(id);
				for(int i = 0; i<feed.size(); i++) {
					if(viewedPosts.contains(qfeed.peek().getId())) {
						PledgeFeedItem pfi = qfeed.poll();
						qfeed.add(pfi);
					}
					else {
						ls.add(qfeed.poll());
					}
				}
				while(!(qfeed.isEmpty())) {
					ls.add(qfeed.poll());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ls;

	}
	
	public PledgeFeedItem getPostById(String postid) {
		RestHighLevelClient client = esConfig.getElastcsearchClient();	
		PledgeFeedItem ufi = null;
		SearchRequest searchRequest = new SearchRequest("user_feed_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		
		sourceBuilder.query(QueryBuilders.termQuery("id", postid));
		searchRequest.source(sourceBuilder);
		try {
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			
			Map<String, Object> src = null;
			Map<String, Object> action = null;
			Map<String, Object> target = null;
			Map<String, Object> actor = null;
			Map<String, Object> detailActions = null;
			
			SearchHit[] hits = searchResponse.getHits().getHits();
			src = hits[0].getSourceAsMap();
			action = (Map<String, Object>)src.get("action_object");
			target = (Map<String, Object>)src.get("target_object");
			actor = (Map<String, Object>)src.get("actor");
			detailActions = (Map<String, Object>)src.get("detail_actions");
			Object detail = src.get("detail");
			Object actionDate = src.get("date_stamp");
			Object feedId = src.get("id");
			
			ufi = new PledgeFeedItem(parseActor(actor), (String) detail, parseDetailActions(detailActions), parseActionTarget(action));
			ufi.setTarget(parseActionTarget(target));
			ufi.setId((String)feedId);
			if(actionDate != null && !actionDate.toString().isEmpty())
				ufi.setActionDate(Long.valueOf(actionDate.toString()));
			
			
			
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return ufi;
	}
	
	private GenericItem<String> parseActor(Map<String, Object> values) {
		GenericItem<String> gi = new GenericItem<String>();
		if(values == null) {
			return null;
		}
		Object object = values.get("id");
		if(object != null) {
			gi.setId((String) object);
		}
		object = values.get("name");
		if(object != null) {
			gi.setName((String) object);
		}
		object = values.get("permalink");
		if(object != null) {
			gi.setPermalink((String) object);
		}
		object = values.get("picture_link");
		if(object != null) {
			gi.setPictureLink((String) object);
		}
		return gi;
	}

	private FeedItemDetailActions parseDetailActions(Map<String, Object> values) {
		FeedItemDetailActions tda = new FeedItemDetailActions();
		if(values == null)
			return null;
		Object object = values.get("likes");
		if(object!= null)
			tda.setLikes((Integer)values.get("likes"));
		object = values.get("shares");
		if(object!= null)
			tda.setShares((Integer)values.get("shares"));
		object = values.get("comment");
		if(object!= null)
			tda.setComment((Integer)values.get("comment"));
		return tda;
	}

	private GenericActionTarget parseActionTarget(Map<String, Object> values) {
		GenericActionTarget gat = new GenericActionTarget();
		Object object = values.get("id");
		if(object!= null)
			gat.setId(object.toString());
		object = values.get("name");
		if(object!= null)
			gat.setName(object.toString());
		object = values.get("summary");
		if(object!= null)
			gat.setSummary(object.toString());
		object = values.get("permalink");
		if(object!= null)
			gat.setPermalink(object.toString());
		object = values.get("action_type");
		if(object!= null)
			gat.setActionType(UserActionTargetType.valueOf(object.toString()));

		return gat;
	}

	private List<String> getViewedPost(String id) {
		SearchRequest searchRequest = new SearchRequest("user_feed_view_action_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.termQuery("userId", id));
		searchRequest.source(sourceBuilder);
		
		RestHighLevelClient client = esConfig.getElastcsearchClient();
		try {
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			List<String> viewedPosts = new ArrayList<String>();
			for(SearchHit hit: searchResponse.getHits()) {
				Map<String,Object> src = hit.getSourceAsMap();
				viewedPosts.add((String) src.get("postId"));
			}
			return viewedPosts;
		} catch(IOException e) {
			e.printStackTrace();
			return new ArrayList<String>();
		}
	}
	
	public  List<String> getBeneficiaryId(String id) {
		SearchRequest searchRequest = new SearchRequest("beneficiary_follow_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.nestedQuery("user_follower",QueryBuilders.boolQuery().must(QueryBuilders.termQuery("user_follower.id", id)), ScoreMode.None));
		searchRequest.source(sourceBuilder);

		RestHighLevelClient client = esConfig.getElastcsearchClient();
		try {
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			List<String> bens = new ArrayList<String>();
			for(SearchHit hit: searchResponse.getHits()) {
				Map<String, Object> benObj = (Map<String, Object>)(hit.getSourceAsMap()).get("beneficiary_followee");
				bens.add((String) benObj.get("id"));
			}
			return bens;
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<String>();
		}
	}


	public  List<String> getUserId(String id) {
		SearchRequest searchRequest = new SearchRequest("user_follow_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.nestedQuery("user_follower",QueryBuilders.boolQuery().must(QueryBuilders.termQuery("user_follower.id", id)), ScoreMode.None));
		searchRequest.source(sourceBuilder);

		RestHighLevelClient client = esConfig.getElastcsearchClient();
		try {
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			List<String> users = new ArrayList<String>();
			for(SearchHit hit: searchResponse.getHits()) {
				Map<String, Object> user = (Map<String, Object>)(hit.getSourceAsMap()).get("user_followee");
				users.add((String) user.get("id"));
			}
			return users;
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<String>();
		}

	}
	
	public void addComment(FeedComment comment) throws JsonProcessingException {
		IndexRequest indexRequest = new IndexRequest("user_feed_comment_idx").source(new ObjectMapper().writeValueAsString(comment),XContentType.JSON);
		indexRequest.opType(DocWriteRequest.OpType.INDEX);
		try {
			IndexResponse indexResponse = esConfig.getElastcsearchClient().index(
					indexRequest, RequestOptions.DEFAULT);
			if (indexResponse.getResult() != DocWriteResponse.Result.CREATED)
				throw new IllegalStateException("Comment was not created for post id: " + comment.getPostId());
			else
			{
				log.info("Comment created successfully");
			}
		} catch (IOException e) {

			log.error("Unable to create comment document in ES", e);
		}
	}
	
	public List<FeedComment> getFeedComments(String postid) {
		SearchRequest searchRequest = new SearchRequest("user_feed_comment_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.termQuery("post_id", postid)).sort(SortBuilders.fieldSort("comment_date").order(SortOrder.DESC));
		searchRequest.source(sourceBuilder);
		
		RestHighLevelClient client = esConfig.getElastcsearchClient();
		List<FeedComment> comments = new ArrayList<FeedComment>();
		try {
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			FeedComment feedComment = null;
			Map<String, Object> src = null;
			Map<String, Object> commentor = null;
			for (SearchHit hit: searchResponse.getHits()) {
				src = hit.getSourceAsMap();
				commentor = (Map<String, Object>) src.get("commenter");
				Object commentMessage  = src.get("comment");
				Object commentDate = src.get("comment_date");
				feedComment = new FeedComment();
				feedComment.setComment(commentMessage.toString());
				feedComment.setComment_date(Long.valueOf(commentDate.toString()));
				feedComment.setPostId(postid);
				feedComment.setCommenter(parseActor(commentor));
				comments.add(feedComment);
			} 
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return comments;
	}
	public List<String> getLikedFeedId(String userid) {
		// TODO Auto-generated method stub
		SearchRequest searchRequest = new SearchRequest("user_feed_like_action_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.termQuery("userId", userid));
		searchRequest.source(sourceBuilder);
		
		RestHighLevelClient client = esConfig.getElastcsearchClient();
		List<String> likedFeedId = new ArrayList<String>();
		try {
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			Map<String , Object> src = null;
			for (SearchHit hit: searchResponse.getHits()) {
				src = hit.getSourceAsMap();
				String id = src.get("postId").toString();
				likedFeedId.add(id);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return likedFeedId;
	}

	
} 
