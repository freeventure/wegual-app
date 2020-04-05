package com.wegual.userservice.es.actions;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.wegual.userservice.ElasticSearchConfig;

import app.wegual.common.model.UserTimelineItem;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserFollowActions {
	
	private static String USER_FOLLOW_INDEX = "user_follow_idx";

	@Autowired
	private ElasticSearchConfig esConfig;
	
	@Autowired
	@Qualifier("executorESActions")
	TaskExecutor te;
	
	protected void processAsynch(ESUserAction action, UserTimelineItem uti) {
		te.execute(new ESAsyncCommandRunner(action, uti));
	}
	
	public void userFollowed(UserTimelineItem uti) {
		
		ESUserAction action = this::asynchUserFollowed;
		processAsynch(action, uti);
		
	}
	
	protected void asynchUserFollowed(UserTimelineItem uti)
	{
		try {
			IndexRequest indexRequest = new IndexRequest(USER_FOLLOW_INDEX)
			        .source(jsonBuilder()
			                .startObject()
			                .field("follower_user_id", uti.getTarget().getId())
			                .field("date_stamp", uti.getActionDate())
			                .field("followee_user_id", uti.getActorId())
			            .endObject());
			esConfig.getElastcsearchClient().index(indexRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.error("Error inserting user follow event in index: " + uti.getActorId(), e);
		}
	}
	
	public void userUnfollowed(UserTimelineItem uti) {
		ESUserAction action = this::asynchUserUnfollowed;
		processAsynch(action, uti);
	}

	public void asynchUserUnfollowed(UserTimelineItem uti) {
		
		DeleteByQueryRequest request = new DeleteByQueryRequest(USER_FOLLOW_INDEX); 
		
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
				.must(QueryBuilders.termQuery("follower_user_id", uti.getTarget().getId()))
				.must(QueryBuilders.termQuery("followee_user_id", uti.getActorId()));
				
		request.setQuery(boolQuery);
		request.setSize(1);
		request.setBatchSize(1);
		request.setRefresh(true);
		
		try {
			BulkByScrollResponse bulkResponse = esConfig.getElastcsearchClient().deleteByQuery(request, RequestOptions.DEFAULT);
			if(bulkResponse.getDeleted() != 1)
				throw new IllegalStateException("exactly one document was not deleted");
		} catch (IOException e) {
			
			log.error("Unable to delete by query. follower: " + 
					uti.getTarget().getId() +
					" followee: " + uti.getActorId(), e);
		}
		
	}
}
