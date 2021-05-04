package com.wegual.userservice.es.actions;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentFactory;
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
			IndexResponse indexResponse = esConfig.getElastcsearchClient()
					.index(indexRequest, RequestOptions.DEFAULT);
			if (indexResponse.getResult() != DocWriteResponse.Result.CREATED)
				throw new IllegalStateException("Feed document was not created for id: " + indexResponse.getId());
			else
			{
				log.info("Feed document created successfully for id: " + indexResponse.getId());
				UpdateRequest updateRequest = new UpdateRequest();
				updateRequest.index(USER_FEED_INDEX);
				updateRequest.id(indexResponse.getId());
				updateRequest.doc( XContentFactory.jsonBuilder()
				        .startObject()
				            .field("id",indexResponse.getId() )
				        .endObject());
				UpdateResponse update = esConfig.getElastcsearchClient().update(updateRequest, RequestOptions.DEFAULT);
				log.info("Feed Document update with id " + update.toString());
			}
		} catch (Exception e) {
			log.error("Error inserting user feed event in index: " + pfi.getActor().getId(), e);
		} 
	}
}
