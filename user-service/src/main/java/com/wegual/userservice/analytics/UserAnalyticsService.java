package com.wegual.userservice.analytics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wegual.userservice.ElasticSearchConfig;

import app.wegual.common.model.Beneficiary;
import app.wegual.common.model.User;
import app.wegual.common.rest.model.UserFollowees;
import app.wegual.common.rest.model.UserFollowers;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserAnalyticsService {

	@Autowired
	private ElasticSearchConfig esConfig;
	
	
	// Recommend which users to follow
	// for now simply return anyone the user is not following 
	public List<User> getUserFollowRecommendations(String userId) {
		List<User> usersFound = new ArrayList<>();
		User user = null;
		SearchRequest searchRequest = new SearchRequest("user_follow_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		BoolQueryBuilder bb = QueryBuilders.boolQuery();
		bb.mustNot().add(QueryBuilders.termQuery("followee_user_id", userId));
		sourceBuilder.query(bb); 
		sourceBuilder.size(0); 		
		searchRequest.source(sourceBuilder);
		
		try {
			RestHighLevelClient client = esConfig.getElastcsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			
			for (SearchHit hit: searchResponse.getHits()) {
				user = new User(); 
				user.setId(hit.getId());
				usersFound.add(user);
			}
		} catch (IOException e) {
			log.error("Error getting follower count for user: " + userId , e);
		}
		
		return usersFound;
	}
	
	// get follower count for a user
	public UserFollowers followersCount(String userId) {
		
		SearchRequest searchRequest = new SearchRequest("user_follow_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.termQuery("followee_user_id", userId)); 
		sourceBuilder.size(0); 		
		searchRequest.source(sourceBuilder);
		
		try {
			RestHighLevelClient client = esConfig.getElastcsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			return new UserFollowers(userId, searchResponse.getHits().getTotalHits().value);
		} catch (IOException e) {
			log.error("Error getting follower count for user: " + userId , e);
			return new UserFollowers(userId, 0L);
		}
	}
	// get following count for a user (where user is a follower of someone)
	public UserFollowees followingCount(String userId) {
		
		SearchRequest searchRequest = new SearchRequest("user_follow_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.termQuery("follower_user_id", userId)); 
		sourceBuilder.size(0); 		
		searchRequest.source(sourceBuilder);
		
		try {
			RestHighLevelClient client = esConfig.getElastcsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			return new UserFollowees(userId, searchResponse.getHits().getTotalHits().value);
		} catch (IOException e) {
			log.error("Error getting follower count for user: " + userId , e);
			return new UserFollowees(userId, 0L);
		}
	}
}
