package com.wegual.giveupservice.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import app.wegual.common.asynch.MessageSender;
import app.wegual.common.model.GiveUpTimelineItem;
import app.wegual.common.model.UserTimelineItem;
import app.wegual.common.util.MessagingConstants;
import lombok.extern.slf4j.Slf4j;

@RefreshScope
@Component
@Slf4j
public class TimelineMessageSender implements MessageSender{

	private final RabbitTemplate rabbitTemplate;

	public TimelineMessageSender(RabbitTemplate template) {
		this.rabbitTemplate = template;
	}

	protected void sendMessage(UserTimelineItem uti) {
		 log.info("Sending message for user timeline action...");
		 rabbitTemplate.convertAndSend(MessagingConstants.USERACTIONS_FANOUT_EXCHANGE_NAME, MessagingConstants.USER_TIMELINE_QUEUE_NAME, uti);
	}
	
	protected void sendMessageToGiveUp(GiveUpTimelineItem gti) {
		 log.info("Sending message for giveup timeline action...");
		 rabbitTemplate.convertAndSend(MessagingConstants.GIVEUPACTIONS_FANOUT_EXCHANGE_NAME, MessagingConstants.GIVEUP_ACTION_QUEUE_NAME, gti);
	}

	@Override
	public void sendMessage(Object object) {
		if(object != null) {
			if(object instanceof UserTimelineItem) {
				this.sendMessage((UserTimelineItem)object);
			}
			if(object instanceof GiveUpTimelineItem) {
				this.sendMessageToGiveUp((GiveUpTimelineItem)object);
			}
		}
	}

}
