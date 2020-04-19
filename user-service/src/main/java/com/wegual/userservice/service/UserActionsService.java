package com.wegual.userservice.service;

import java.util.Map;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.wegual.userservice.ElasticSearchConfig;
import com.wegual.userservice.UserUtils;
import com.wegual.userservice.message.UserActionsAsynchMessageSender;

import app.wegual.common.asynch.SenderRunnable;
import app.wegual.common.model.User;
import app.wegual.common.model.UserTimelineItem;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserActionsService {
	
	private static String USER_INDEX = "user_idx";

	@Autowired
	@Qualifier("executorMessageSender")
	TaskExecutor te;
	
	@Autowired
	private ElasticSearchConfig esConfig;
	
	@Autowired
	private UserActionsAsynchMessageSender uaam;
	
	public void followUser(String userId, String actorId) {
		
	}
	
	public void unfollowUser(String userId, String actorId) {
		
	}

	// gets a user document from ES given username
	public User getUserDocument(String username) {
		SearchRequest searchRequest = new SearchRequest(USER_INDEX);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.termQuery("username", username)).size(1);
		searchRequest.source(sourceBuilder);
		
		try {
			RestHighLevelClient client = esConfig.getElastcsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			if(searchResponse.getHits().getTotalHits().value > 0L)
			{
				Map<String, Object> src = null;
				for (SearchHit hit: searchResponse.getHits()) {
					src = hit.getSourceAsMap();
					User user = UserUtils.userFromESDocument(src);
					return user;
				}
			}
			else
				return null;
		} catch (Exception e) {
			log.error("Error getting document for user: " + username , e);
		}
		return null;
	}

	// creates a User document in ES with the given User
	public void createUserDocument(User user) {
		
	}
	
	public void updateProfile() {
		
	}
	
//	public void updateProfilePicture(String userId, String pictureId) {
//		try {
//			IndexRequest indexRequest = new IndexRequest(USER_INDEX, "_doc")
//			        .source(new UserTimelineIndexAdapter().indexJson(uti));
//			esConfig.getElastcsearchClient().index(indexRequest, RequestOptions.DEFAULT);
//		} catch (Exception e) {
//			log.error("Error inserting user timeline event in index: " + uti.getActorId(), e);
//		} 
//	}

	protected void sendMessageAsynch(UserTimelineItem uti) {
		te.execute(new SenderRunnable<UserActionsAsynchMessageSender, UserTimelineItem>(uaam, uti));
	}
	
}
