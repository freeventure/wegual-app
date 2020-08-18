package com.wegual.beneficiaryservice.es.actions;

import app.wegual.common.model.BeneficiaryTimelineItem;

@FunctionalInterface
public interface ESBeneficiaryAction {

	void theAction(BeneficiaryTimelineItem bti);
	
}
