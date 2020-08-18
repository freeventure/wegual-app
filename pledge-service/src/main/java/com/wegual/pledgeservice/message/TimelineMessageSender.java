package com.wegual.pledgeservice.message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import app.wegual.common.asynch.MessageSender;
import app.wegual.common.model.BeneficiaryTimelineItem;
import app.wegual.common.model.UserTimelineItem;
import app.wegual.common.util.MessagingConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TimelineMessageSender implements MessageSender{

	private final RabbitTemplate rabbitTemplate;

	public TimelineMessageSender(RabbitTemplate template) {
		this.rabbitTemplate = template;
	}

	protected void sendMessage(UserTimelineItem uti) {
		log.info("Sending message for user timeline action...");
		rabbitTemplate.convertAndSend(MessagingConstants.USERACTIONS_FANOUT_EXCHANGE_NAME, MessagingConstants.USER_TIMELINE_QUEUE_NAME, uti);
	}
	
	protected void sendMessageToBeneficiary(BeneficiaryTimelineItem bti) {
		 log.info("Sending message for beneficiary timeline action...");
		 rabbitTemplate.convertAndSend(MessagingConstants.BENEFICIARY_FANOUT_EXCHANGE_NAME, MessagingConstants.BENEFICIARY_TIMELINE_QUEUE_NAME, bti);
	}
	
	@Override
	public void sendMessage(Object object) {
		if(object != null) {
			if(object instanceof UserTimelineItem) {
				this.sendMessage((UserTimelineItem)object);
			}
			if(object instanceof BeneficiaryTimelineItem) {
				this.sendMessageToBeneficiary((BeneficiaryTimelineItem)object);
			}
		}
	}
}
