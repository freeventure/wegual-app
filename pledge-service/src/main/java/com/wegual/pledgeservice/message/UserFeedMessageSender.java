package com.wegual.pledgeservice.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import app.wegual.common.asynch.MessageSender;
import app.wegual.common.model.PledgeFeedItem;
import app.wegual.common.util.MessagingConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserFeedMessageSender implements MessageSender{

	private final RabbitTemplate rabbitTemplate;

	public UserFeedMessageSender(RabbitTemplate template) {
		this.rabbitTemplate = template;
	}

	protected void sendMessage(PledgeFeedItem pfi) {
		log.info("Sending message for user feed action...");
		rabbitTemplate.convertAndSend(MessagingConstants.USERACTIONS_FANOUT_EXCHANGE_NAME, MessagingConstants.USER_FEED_QUEUE_NAME, pfi);
	}

	@Override
	public void sendMessage(Object object) {
		if(object != null) {
			if(object instanceof PledgeFeedItem) {
				this.sendMessage((PledgeFeedItem)object);
			}
		}
	}
}
