package app.wegual.poc.mail.messaging;

import java.io.IOException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.wegual.poc.common.util.MessagingConstants;
import app.wegual.poc.mail.service.MailService;

@Service
public class LoginReminderReciever {
	
	@Autowired 
	MailService ms;
	
	@RabbitListener(queues = MessagingConstants.queueNameLoginReminderMailService)
	public void receiveMessageForLoginReminder(String message) throws IOException {
		System.out.println("login reminder msg received");
		String[] userDetails = message.split(" ");
		ms.prepareAndSendLoginReminderMail(userDetails[0], userDetails[1]);
		
	}
}

	
	
