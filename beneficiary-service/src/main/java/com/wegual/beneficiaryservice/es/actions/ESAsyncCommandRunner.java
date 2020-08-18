package com.wegual.beneficiaryservice.es.actions;

import app.wegual.common.model.BeneficiaryTimelineItem;

public class ESAsyncCommandRunner implements Runnable{
	
	private ESBeneficiaryAction action;
	private BeneficiaryTimelineItem bti;
	
	public ESAsyncCommandRunner(ESBeneficiaryAction action, BeneficiaryTimelineItem bti) {
		this.action = action;
		this.bti = bti;
	}
	
	public void run() {
		action.theAction(bti);
	}

}
