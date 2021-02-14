package com.wegual.webapp.message;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import app.wegual.common.asynch.MessageSender;
import app.wegual.common.model.UserActionItem;
import app.wegual.common.model.UserActionType;
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
		rabbitTemplate.convertAndSend(MessagingConstants.USERACTIONS_FANOUT_EXCHANGE_NAME, MessagingConstants.USER_TIMELINE_QUEUE_NAME, uti);
	}

	protected void sendMessageToGiveUp(UserActionItem uat) {
		log.info("Sending message for giveup action...");
		rabbitTemplate.convertAndSend(MessagingConstants.GIVEUPACTIONS_FANOUT_EXCHANGE_NAME, MessagingConstants.GIVEUP_ACTION_QUEUE_NAME, uat);
	}

	protected void sendMessageToBeneficiary(UserActionItem uat) {
		log.info("Sending message for beneficiary action...");
		rabbitTemplate.convertAndSend(MessagingConstants.BENEFICIARY_FANOUT_EXCHANGE_NAME, MessagingConstants.BENEFICIARY_ACTION_QUEUE_NAME, uat);
	}

	@Override
	public void sendMessage(Object object) {
		List<UserActionType> giveupAction = new ArrayList<UserActionType>(Arrays.asList(
				UserActionType.LIKE, 
				UserActionType.UNLIKE
				));
		List<UserActionType> benAction = new ArrayList<UserActionType>(Arrays.asList(
				UserActionType.FOLLOW_BENEFICIARY, 
				UserActionType.UNFOLLOW_BENEFICIARY
				));
		if(object != null) {
			if(object instanceof UserTimelineItem)
				this.sendMessage((UserTimelineItem)object);
			if( object instanceof UserActionItem && giveupAction.contains(((UserActionItem) object).getActionType()))
				this.sendMessageToGiveUp((UserActionItem)object);
			if(object instanceof UserActionItem && benAction.contains(((UserActionItem) object).getActionType()))
					this.sendMessageToBeneficiary((UserActionItem) object);
		}
	}

}
