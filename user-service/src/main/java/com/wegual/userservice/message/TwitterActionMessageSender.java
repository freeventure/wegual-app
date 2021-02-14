package com.wegual.userservice.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import app.wegual.common.util.MessagingConstants;

@Component
public class TwitterActionMessageSender {
	
	private final RabbitTemplate rabbitTemplate;
	
	public TwitterActionMessageSender(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void sendMessage(String userId) {
		System.out.println("Sending message to fetch tweets from twitter");
		rabbitTemplate.convertAndSend(MessagingConstants.TWITTER_ACTION_EXCHANGE, MessagingConstants.TWITTER_ACTION_ROUTING_KEY, userId);
	}

}
