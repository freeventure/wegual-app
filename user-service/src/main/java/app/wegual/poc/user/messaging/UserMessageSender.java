package app.wegual.poc.user.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import app.wegual.poc.common.model.Timeline;
import app.wegual.poc.common.util.MessagingConstants;

@Component
public class UserMessageSender implements MessageSender<Timeline>{

	private final RabbitTemplate rabbitTemplate;
	
	public UserMessageSender(RabbitTemplate rabbitTemplate)
	{
		this.rabbitTemplate= rabbitTemplate;
	}
	
	@Override
	public void sendMessage(Timeline userTimeline) throws AmqpException{
		System.out.println("Sending message for user");
		rabbitTemplate.convertAndSend(MessagingConstants.directExchange, MessagingConstants.userRoutingKey, userTimeline);
	}
}
