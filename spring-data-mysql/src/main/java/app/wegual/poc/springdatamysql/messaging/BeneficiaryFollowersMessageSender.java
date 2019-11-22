package app.wegual.poc.springdatamysql.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import app.wegual.poc.common.model.BeneficiaryFollowers;
import app.wegual.poc.common.util.MessagingConstants;

@Component
public class BeneficiaryFollowersMessageSender {
	private final RabbitTemplate rabbitTemplate;
	
	public BeneficiaryFollowersMessageSender(RabbitTemplate template) {
		this.rabbitTemplate = template;
	}
	
	public void sendMessage(BeneficiaryFollowers ben) throws AmqpException {
		// TODO Auto-generated method stub
		System.out.println("Sending message for beneficary...");
        rabbitTemplate.convertAndSend(MessagingConstants.directExchange, "beneficiary", ben);
	}
	
}