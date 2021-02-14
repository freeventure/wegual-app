package com.wegual.webapp.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import app.wegual.common.util.MessagingConstants;

@Component
public class TwitterMessageSender {
	
	private final RabbitTemplate rabbitTemplate;
	
	public TwitterMessageSender(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void sendMessage(String userId) {
		System.out.println("Sending message to fetch tweets from twitter " + userId);
		rabbitTemplate.convertAndSend(MessagingConstants.TWITTER_ACTION_EXCHANGE, MessagingConstants.TWITTER_ACTION_ROUTING_KEY, userId);
	}

}
