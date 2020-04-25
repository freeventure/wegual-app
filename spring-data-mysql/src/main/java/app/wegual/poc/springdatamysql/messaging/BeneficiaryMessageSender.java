package app.wegual.poc.springdatamysql.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import app.wegual.common.model.Beneficiary;
import app.wegual.common.util.MessagingConstants;

@Component
public class BeneficiaryMessageSender {
	private final RabbitTemplate rabbitTemplate;
	
	public BeneficiaryMessageSender(RabbitTemplate template) {
		this.rabbitTemplate = template;
	}
	
	public void sendMessage(Beneficiary ben) throws AmqpException {
		// replace with logger
        System.out.println("Sending message for beneficary...");
        rabbitTemplate.convertAndSend(MessagingConstants.EXCHANGE_NAME, "beneficiaries", ben);
	}
}
