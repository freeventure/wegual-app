package app.wegual.poc.restmsgsender;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import app.wegual.poc.common.model.ObjectCreate;
import app.wegual.poc.common.util.MessagingConstants;

@Component
public class ObjectCreateMessageSender {
	
	private final RabbitTemplate rabbitTemplate;

	public ObjectCreateMessageSender(RabbitTemplate template) {
		this.rabbitTemplate = template;
	}

public void sendMessage(ObjectCreate payload) throws AmqpException {

       System.out.println("Sending message...");
       rabbitTemplate.convertAndSend(MessagingConstants.EXCHANGE_NAME, MessagingConstants.ES_CAS_ROUTING_KEY, payload);
	}
}