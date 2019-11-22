package app.wegual.poc.springdatamysql.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import app.wegual.poc.common.model.BeneficiaryTimeline;
import app.wegual.poc.common.util.MessagingConstants;

@Component
public class BeneficiaryMessageSender {
	private final RabbitTemplate rabbitTemplate;
	
	public BeneficiaryMessageSender(RabbitTemplate template) {
		this.rabbitTemplate = template;
	}
	
	public void sendMessage(BeneficiaryTimeline benTimeline) throws AmqpException {
		// replace with logger
        System.out.println("Sending message for beneficaryTimeline...");
        rabbitTemplate.convertAndSend(MessagingConstants.directExchange, "beneficiary", benTimeline);
	}
}
