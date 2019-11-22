package app.wegual.poc.springdatamysql.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import app.wegual.poc.common.model.GiveUpFollower;
import app.wegual.poc.common.util.MessagingConstants;

@Component
public class GiveUpFollowerMessageSender {
	private final RabbitTemplate rabbitTemplate;
	
	public GiveUpFollowerMessageSender(RabbitTemplate template) {
		this.rabbitTemplate = template;
	}
	
	public void sendMessage(GiveUpFollower ben) throws AmqpException {
		// TODO Auto-generated method stub
		System.out.println("Sending message for beneficary...");
        rabbitTemplate.convertAndSend(MessagingConstants.directExchange, "user", ben);
	}
	

}