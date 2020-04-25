package com.wegual.beneficiaryservice.es.actions;

import app.wegual.common.model.BeneficiaryTimelineItem;

public class ESAsyncCommandRunner implements Runnable {
	
	private ESBeneficiaryAction action;
	private BeneficiaryTimelineItem data;
	
	public ESAsyncCommandRunner(ESBeneficiaryAction action, BeneficiaryTimelineItem uti) {
		this.action = action;
		data = uti;
	}
	
	public void run() {
		action.theAction(data);
	}
}