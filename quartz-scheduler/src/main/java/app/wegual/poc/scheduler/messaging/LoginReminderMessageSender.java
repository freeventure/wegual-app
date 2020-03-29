//package app.wegual.poc.scheduler.messaging;
//import java.util.List;
//
//import org.springframework.amqp.AmqpException;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.stereotype.Component;
//import app.wegual.poc.common.util.MessagingConstants;
////import app.wegual.poc.es.model.User;
//
//@RefreshScope
//@Component
//public class LoginReminderMessageSender {
//	
//	private final RabbitTemplate rabbitTemplate;
//	
//	public LoginReminderMessageSender(RabbitTemplate rabbitTemplate)
//	{
//		this.rabbitTemplate= rabbitTemplate;
//	}
//	
//	public void sendMessage(String msg) throws AmqpException{
//		System.out.println("Sending message from scheduler service for login reminder " + msg);
//		rabbitTemplate.convertAndSend(MessagingConstants.directExchange, MessagingConstants.userServiceSchedulerRoutingKey, msg);
//	}
//
//}



	
	
	
	

