package app.wegual.poc.user.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import app.wegual.poc.common.util.MessagingConstants;


@Component
public interface MessageSender<O>{
	
	public void sendMessage(O message);
	
}
