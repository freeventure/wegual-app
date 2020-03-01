package app.wegual.poc.scheduler.messaging;
import java.util.List;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import app.wegual.poc.common.util.MessagingConstants;
import app.wegual.poc.es.model.User;

@Component
public class LoginReminderMessageSender {
	
	private final RabbitTemplate rabbitTemplate;
	
	public LoginReminderMessageSender(RabbitTemplate rabbitTemplate)
	{
		this.rabbitTemplate= rabbitTemplate;
	}
	
	public void sendMessage(List<User> inactiveUsers) throws AmqpException{
		System.out.println("Sending message for login reminder");
		rabbitTemplate.convertAndSend(MessagingConstants.directExchange, MessagingConstants.loginReminderRoutingKey, inactiveUsers);
	}

}



	
	
	
	

