package com.wegual.beneficiaryservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.wegual.beneficiaryservice.message.BeneficiaryActionsAsynchMessageSender;

import app.wegual.common.asynch.SenderRunnable;
import app.wegual.common.model.BeneficiaryTimelineItem;

@Service
public class BeneficiaryActionsService {

	@Autowired
	@Qualifier("executorMessageSender")
	TaskExecutor te;
	
	@Autowired
	private BeneficiaryActionsAsynchMessageSender baam;
	
	public void updateProfile() {
		
	}

	protected void sendMessageAsynch(BeneficiaryTimelineItem bti) {
		te.execute(new SenderRunnable<BeneficiaryActionsAsynchMessageSender, BeneficiaryTimelineItem>(baam, bti));
	}
	
}
