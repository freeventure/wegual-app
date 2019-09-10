package app.wegual.poc.springdatamysql.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import app.wegual.poc.common.model.Pledge;
import app.wegual.poc.common.util.MessagingConstants;

@Component
public class PledgeMessageSender {
	private final RabbitTemplate rabbitTemplate;
	
	public PledgeMessageSender(RabbitTemplate template) {
		this.rabbitTemplate = template;
	}
	
	public void sendMessage(Pledge pledge) throws AmqpException {
		// replace with logger
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(MessagingConstants.EXCHANGE_NAME, "pledges", pledge);
	}
}
