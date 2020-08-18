package com.wegual.beneficiaryservice.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wegual.beneficiaryservice.es.actions.BeneficiaryTimelineActions;

import app.wegual.common.model.BeneficiaryTimelineItem;
import app.wegual.common.util.MessagingConstants;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BeneficiaryTimelineMessageReceiver {
	
	@Autowired
	private BeneficiaryTimelineActions bta;
	
	@RabbitListener(queues = MessagingConstants.BENEFICIARY_TIMELINE_QUEUE_NAME)
	public void receiveMessage(BeneficiaryTimelineItem bti) {
		log.info("ES Received user action message to follow beneficiary");
		bta.beneficiaryTimelineGenericEvent(bti);
	}

}
