package com.wegual.scheduler.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import app.wegual.common.asynch.MessageSender;
import app.wegual.common.message.MailServiceMessage;
import app.wegual.common.util.MessagingConstants;

@RefreshScope
@Component
public class SchedulerMailMessageSender implements MessageSender {
	private final RabbitTemplate rabbitTemplate;
	
	public SchedulerMailMessageSender(RabbitTemplate template) {
		this.rabbitTemplate = template;
	}
	
	protected void sendMessage(MailServiceMessage msm) {
		 System.out.println("Sending message for mail service action...");
		 rabbitTemplate.convertAndSend(MessagingConstants.MAILSERVICE_EXCHANGE_NAME, "mail-service-action", msm);
	}
	
	@Override
	public void sendMessage(Object object) {
		if(object != null) {
			if(object instanceof MailServiceMessage)
				this.sendMessage((MailServiceMessage)object);
		}
	}
}
