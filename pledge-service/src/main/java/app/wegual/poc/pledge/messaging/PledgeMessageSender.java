package app.wegual.poc.pledge.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import app.wegual.poc.common.messaging.MessageSender;
import app.wegual.poc.common.model.Timeline;
import app.wegual.poc.common.util.MessagingConstants;


@Component
public class PledgeMessageSender implements MessageSender<Timeline> {
	
	private final RabbitTemplate rabbitTemplate;
	
	public PledgeMessageSender(RabbitTemplate rabbitTemplate)
	{
		this.rabbitTemplate= rabbitTemplate;
	}
	
	@Override
	public void sendMessage(Timeline timeline) throws AmqpException{
		System.out.println("Sending message for pledge");
		rabbitTemplate.convertAndSend(MessagingConstants.fanoutExchange,"", timeline);
	}
}