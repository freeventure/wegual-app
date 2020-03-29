package app.wegual.poc.user.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import app.wegual.poc.common.messaging.MessageSender;
import app.wegual.poc.common.model.Timeline;
import app.wegual.poc.common.util.MessagingConstants;

@Component
public class LoginReminderMessageSender implements MessageSender<String>{
	
	private final RabbitTemplate rabbitTemplate;
	
	public LoginReminderMessageSender(RabbitTemplate rabbitTemplate)
	{
		this.rabbitTemplate= rabbitTemplate;
	}
	
	public void sendMessage(String message) throws AmqpException{
		System.out.println("Sending message for login reminder to mail service: " + message);
		rabbitTemplate.convertAndSend(MessagingConstants.directExchange, MessagingConstants.loginReminderRoutingKey, message);
	}

}



	
	