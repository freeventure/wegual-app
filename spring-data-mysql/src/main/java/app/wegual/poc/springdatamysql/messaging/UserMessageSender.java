package app.wegual.poc.springdatamysql.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import app.wegual.poc.common.model.UserTimeline;
import app.wegual.poc.common.util.MessagingConstants;

@Component
public class UserMessageSender {
private final RabbitTemplate rabbitTemplate;
	
	public UserMessageSender(RabbitTemplate template) {
		this.rabbitTemplate = template;
	}
	
	public void sendMessage(UserTimeline userTimeline) throws AmqpException {
		// replace with logger
        System.out.println("Sending message for userTimeline...");
        rabbitTemplate.convertAndSend(MessagingConstants.directExchange, "user", userTimeline);
	}
}
