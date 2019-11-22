package app.wegual.poc.springdatamysql.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import app.wegual.poc.common.model.GiveUp;
import app.wegual.poc.common.util.MessagingConstants;

@Component
public class GiveUpMessageSender {
	private final RabbitTemplate rabbitTemplate;
	
	public GiveUpMessageSender(RabbitTemplate template) {
		this.rabbitTemplate = template;
	}
	
	public void sendMessage(GiveUp gu) throws AmqpException {
		// replace with logger
        System.out.println("Sending message for giveup...");
        rabbitTemplate.convertAndSend(MessagingConstants.EXCHANGE_NAME, "giveup", gu);
	}
}
