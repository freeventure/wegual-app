package app.wegual.poc.beneficiary.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import app.wegual.poc.common.messaging.MessageSender;
import app.wegual.poc.common.model.Timeline;
import app.wegual.poc.common.util.MessagingConstants;

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
		rabbitTemplate.convertAndSend(MessagingConstants.directExchange, MessagingConstants.beneficiaryRoutingKey, benTimeline);
	}

}
