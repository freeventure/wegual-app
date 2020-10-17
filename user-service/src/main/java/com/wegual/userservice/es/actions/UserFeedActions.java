package com.wegual.userservice.es.actions;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.wegual.userservice.ElasticSearchConfig;

import app.wegual.common.model.PledgeFeedItem;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserFeedActions {
	
	private static String USER_FEED_INDEX = "user_feed_idx";
	
	@Autowired
	private ElasticSearchConfig esConfig;
	
	@Autowired
	@Qualifier("executorESActions")
	TaskExecutor te;
	
	protected void processAsynch(ESUserFeedAction action, PledgeFeedItem pfi) {
		te.execute(new ESAsyncCommandRunnerForFeed(action, pfi));
	}
	
	public void userFeedGenericEvent(PledgeFeedItem pfi) {
		
		ESUserFeedAction action = this::asynchGenericFeedInsert;
		processAsynch(action, pfi);	
	}
	
	protected void asynchGenericFeedInsert(PledgeFeedItem pfi)
	{
		try {
			IndexRequest indexRequest = new IndexRequest(USER_FEED_INDEX)
			        .source(new UserFeedIndexAdapter().indexJson(pfi));
			esConfig.getElastcsearchClient().index(indexRequest, RequestOptions.DEFAULT);
		} catch (Exception e) {
			log.error("Error inserting user feed event in index: " + pfi.getActor().getId(), e);
		} 
	}
}
