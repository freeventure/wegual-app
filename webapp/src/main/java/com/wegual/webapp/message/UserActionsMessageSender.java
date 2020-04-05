package com.wegual.webapp.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import app.wegual.common.asynch.MessageSender;
import app.wegual.common.model.UserTimelineItem;
import app.wegual.common.util.MessagingConstants;
import lombok.extern.slf4j.Slf4j;

@RefreshScope
@Component
@Slf4j
public class UserActionsMessageSender implements MessageSender {
	private final RabbitTemplate rabbitTemplate;
	
	public UserActionsMessageSender(RabbitTemplate template) {
		this.rabbitTemplate = template;
	}
	
	protected void sendMessage(UserTimelineItem uti) {
		 log.info("Sending message for user timeline action...");
		 rabbitTemplate.convertAndSend(MessagingConstants.USERACTIONS_FANOUT_EXCHANGE_NAME, "user-action", uti);
	}

	@Override
	public void sendMessage(Object object) {
		if(object != null) {
			if(object instanceof UserTimelineItem)
				this.sendMessage((UserTimelineItem)object);
		}
	}

}
