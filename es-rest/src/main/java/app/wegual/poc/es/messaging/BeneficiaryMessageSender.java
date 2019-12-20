package app.wegual.poc.es.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.wegual.poc.common.util.MessagingConstants;
import app.wegual.poc.es.model.Timeline;

@Component
public class BeneficiaryMessageSender implements MessageSender<Timeline>{
	private final RabbitTemplate rabbitTemplate;
	
	public BeneficiaryMessageSender(RabbitTemplate rabbitTemplate)
	{
		this.rabbitTemplate= rabbitTemplate;
	}
	
	@Override
	public void sendMessage(Timeline benTimeline) throws AmqpException{
		System.out.println("Sending message for beneficiary");
		rabbitTemplate.convertAndSend(MessagingConstants.directExchange, MessagingConstants.timelineRoutingKey, benTimeline);
	}
	
}
