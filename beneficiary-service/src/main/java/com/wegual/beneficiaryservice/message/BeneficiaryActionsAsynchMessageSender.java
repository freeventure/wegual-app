package com.wegual.beneficiaryservice.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import app.wegual.common.asynch.MessageSender;
import app.wegual.common.model.BeneficiaryTimelineItem;
import app.wegual.common.util.MessagingConstants;
import lombok.extern.slf4j.Slf4j;

@Component
@RefreshScope
@Slf4j
public class BeneficiaryActionsAsynchMessageSender implements MessageSender {
	private final RabbitTemplate rabbitTemplate;
	
	public BeneficiaryActionsAsynchMessageSender(RabbitTemplate template) {
		this.rabbitTemplate = template;
	}
	
	protected void sendMessage(BeneficiaryTimelineItem bti) {
		 log.info("Sending message for user timeline action...");
		 rabbitTemplate.convertAndSend(MessagingConstants.BENEFICIARY_FANOUT_EXCHANGE_NAME, "beneficiary-action", bti);
	}

	@Override
	public void sendMessage(Object object) {
		if(object != null) {
			if(object instanceof BeneficiaryTimelineItem)
				this.sendMessage((BeneficiaryTimelineItem)object);
		}
	}

}
