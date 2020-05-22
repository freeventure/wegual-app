package com.wegual.userservice.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import app.wegual.common.asynch.MessageSender;
import app.wegual.common.model.GiveUpLike;
import app.wegual.common.model.UserEmailVerifyToken;
import app.wegual.common.model.UserTimelineItem;
import app.wegual.common.util.MessagingConstants;
import lombok.extern.slf4j.Slf4j;

@Component
@RefreshScope
@Slf4j
public class UserActionsAsynchMessageSender implements MessageSender {
	private final RabbitTemplate rabbitTemplate;
	
	public UserActionsAsynchMessageSender(RabbitTemplate template) {
		this.rabbitTemplate = template;
	}
	
	protected void sendUserTimelineMessage(UserTimelineItem uti) {
		 log.info("Sending message for user timeline action...");
		 rabbitTemplate.convertAndSend(MessagingConstants.USERACTIONS_FANOUT_EXCHANGE_NAME, "user-action", uti);
	}

	protected void sendVerificationToken(UserEmailVerifyToken uevt) {
		 log.info("Sending message for user email verification token");
		 rabbitTemplate.convertAndSend(MessagingConstants.MAILSERVICE_EXCHANGE_NAME, MessagingConstants.EMAIL_VERIFY_ROUTING_KEY, uevt);
	}
	
	protected void giveUpLike(GiveUpLike gul) {
		 log.info("Instantiating Giveup Like for userId" + gul.getUser().getId());
		 rabbitTemplate.convertAndSend(MessagingConstants.MAILSERVICE_EXCHANGE_NAME, MessagingConstants.EMAIL_VERIFY_ROUTING_KEY, gul);
	}
	
	@Override
	public void sendMessage(Object object) {
		if(object != null) {
			if(object instanceof UserTimelineItem)
				sendUserTimelineMessage((UserTimelineItem)object);
			if(object instanceof UserEmailVerifyToken)
				sendVerificationToken((UserEmailVerifyToken)object);
			if(object instanceof GiveUpLike)
				giveUpLike((GiveUpLike)object);
		}
	}

}
