package app.wegual.poc.es.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import app.wegual.poc.common.util.MessagingConstants;
import app.wegual.poc.es.model.Timeline;

@Component
public class GiveUpMessageSender implements MessageSender<Timeline> {
	
	private final RabbitTemplate rabbitTemplate;
	
	public GiveUpMessageSender(RabbitTemplate rabbitTemplate)
	{
		this.rabbitTemplate= rabbitTemplate;
	}
	
	@Override
	public void sendMessage(Timeline timeline) throws AmqpException{
		System.out.println("Sending message for giveup");
		rabbitTemplate.convertAndSend(MessagingConstants.directExchange, MessagingConstants.timelineRoutingKey, timeline);
	}
}
