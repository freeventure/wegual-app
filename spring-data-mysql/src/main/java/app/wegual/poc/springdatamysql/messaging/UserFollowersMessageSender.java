package app.wegual.poc.springdatamysql.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import app.wegual.poc.common.model.UserFollowers;
import app.wegual.poc.common.model.UserTimeline;
import app.wegual.poc.common.util.MessagingConstants;

@Component
public class UserFollowersMessageSender {
	private final RabbitTemplate rabbitTemplate;
	
	public UserFollowersMessageSender(RabbitTemplate template) {
		this.rabbitTemplate = template;
	}
	
	public void sendMessage(UserTimeline usr) throws AmqpException {
		// TODO Auto-generated method stub
		System.out.println("Sending message for userFollower...");
        rabbitTemplate.convertAndSend(MessagingConstants.directExchange, "user", usr);
	}
	
}