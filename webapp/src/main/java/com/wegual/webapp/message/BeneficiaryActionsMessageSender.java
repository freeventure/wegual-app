package com.wegual.webapp.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import app.wegual.common.asynch.MessageSender;
import app.wegual.common.model.BeneficiaryTimelineItem;
import app.wegual.common.util.MessagingConstants;
import lombok.extern.slf4j.Slf4j;

@RefreshScope
@Component
@Slf4j
public class BeneficiaryActionsMessageSender implements MessageSender {
private final RabbitTemplate rabbitTemplate;
	
	public BeneficiaryActionsMessageSender(RabbitTemplate template) {
		this.rabbitTemplate = template;
	}
	
	protected void sendMessage(BeneficiaryTimelineItem bti) {
		 log.info("Sending message for beneficiary timeline action...");
		 rabbitTemplate.convertAndSend(MessagingConstants.BENEFICIARY_FANOUT_EXCHANGE_NAME, "ben-action", bti);
	}

	@Override
	public void sendMessage(Object object) {
		if(object != null) {
			if(object instanceof BeneficiaryTimelineItem)
				this.sendMessage((BeneficiaryTimelineItem)object);
		}
	}


}
