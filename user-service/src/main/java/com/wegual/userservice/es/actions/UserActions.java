package com.wegual.userservice.es.actions;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.wegual.userservice.ElasticSearchConfig;

import app.wegual.common.model.UserTimelineItem;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserActions {
	
	private static String USER_INDEX = "user_idx";

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
}
