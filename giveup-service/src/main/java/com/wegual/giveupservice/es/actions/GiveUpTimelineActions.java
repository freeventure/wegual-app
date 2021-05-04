package com.wegual.giveupservice.es.actions;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.wegual.giveupservice.ElasticSearchConfig;

import app.wegual.common.model.GiveUpTimelineItem;
import app.wegual.common.util.ESIndices;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GiveUpTimelineActions {
	
	@Autowired
	private ElasticSearchConfig esConfig;
	
	@Autowired
	@Qualifier("executorESActions")
	TaskExecutor te;
	
	protected void processAsynch(ESGiveUpAction action, GiveUpTimelineItem gti) {
		te.execute(new ESAsyncCommandRunner(action, gti));
	}
	
	public void giveUpTimelineGenericEvent(GiveUpTimelineItem gti) {
		
		ESGiveUpAction action = this::asynchGenericTimelineInsert;
		processAsynch(action, gti);
		
	}
	
	protected void asynchGenericTimelineInsert(GiveUpTimelineItem gti)
	{
		try {
			IndexRequest indexRequest = new IndexRequest(ESIndices.GIVEUP_TIMELINE_INDEX)
			        .source(new GiveUpTimelineIndexAdapter().indexJson(gti));
			esConfig.getElasticsearchClient().index(indexRequest, RequestOptions.DEFAULT);
		} catch (Exception e) {
			log.error("Error inserting user timeline event in index: " + gti.getActorId(), e);
		} 
	}
}
