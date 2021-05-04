package com.wegual.beneficiaryservice.es.actions;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.wegual.beneficiaryservice.ElasticSearchConfig;

import app.wegual.common.model.BeneficiaryTimelineItem;
import app.wegual.common.util.ESIndices;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BeneficiaryTimelineActions {

	@Autowired
	private ElasticSearchConfig esConfig;
	
	@Autowired
	@Qualifier("executorESActions")
	TaskExecutor te;
	
	protected void processAsynch(ESBeneficiaryAction action, BeneficiaryTimelineItem bti) {
		te.execute(new ESAsyncCommandRunner(action, bti));
	}
	
	public void beneficiaryTimelineGenericEvent(BeneficiaryTimelineItem bti) {
		ESBeneficiaryAction action = this::asynchGenericTimelineInsert;
		processAsynch(action, bti);
	}
	
	protected void asynchGenericTimelineInsert(BeneficiaryTimelineItem bti) {
		try {
			IndexRequest indexRequest = new IndexRequest(ESIndices.BENEFICIARY_TIMELINE_INDEX)
			        .source(new BeneficiaryTimelineIndexAdapter().indexJson(bti));
			esConfig.getElasticsearchClient().index(indexRequest, RequestOptions.DEFAULT);
		} catch (Exception e) {
			log.error("Error inserting beneficiary timeline event in index: " + bti.getActorId(), e);
		} 
	}
}
