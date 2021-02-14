package com.wegual.userservice.es.actions;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.wegual.userservice.ElasticSearchConfig;

import app.wegual.common.model.UserTimelineItem;
import app.wegual.common.util.ESIndices;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserTimelineActions {
		
	@Autowired
	private ElasticSearchConfig esConfig;

	@Autowired
	@Qualifier("executorESActions")
	TaskExecutor te;

	protected void processAsynch(ESUserAction action, UserTimelineItem uti) {
		te.execute(new ESAsyncCommandRunner(action, uti));
	}
	
	public void userTimelineGenericEvent(UserTimelineItem uti) {
		
		ESUserAction action = this::asynchGenericTimelineInsert;
		processAsynch(action, uti);
		
	}
	
	protected void asynchGenericTimelineInsert(UserTimelineItem uti)
	{
		try {
			System.out.println(uti.getActionObject().getName());
			IndexRequest indexRequest = new IndexRequest(ESIndices.USER_TIMELINE_INDEX)
			        .source(new UserTimelineIndexAdapter().indexJson(uti));
			esConfig.getElastcsearchClient().index(indexRequest, RequestOptions.DEFAULT);
		} catch (Exception e) {
			log.error("Error inserting user timeline event in index: " + uti.getActorId(), e);
		} 
	}
}
