package app.wegual.poc.springdatamysql.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import java.io.Serializable;
import app.wegual.poc.common.util.MessagingConstants;

public class MessageSender<O>
{
	private final RabbitTemplate rabbitTemplate;
	
	public MessageSender(RabbitTemplate template) {
		this.rabbitTemplate = template;
	}
	public void sendMessage(O timeline,String routingKey) throws AmqpException {
		// replace with logger
        System.out.println("Sending message for timeline...");
        rabbitTemplate.convertAndSend(MessagingConstants.directExchange, routingKey, timeline);
	}
}
