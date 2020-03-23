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
		System.out.println("login reminder msg received" + message);
		String[] userDetails = message.split("#");
		try {
			String recipientEmail =  userDetails[0].substring(1);
			String recipientName = userDetails[1].substring(0, userDetails[1].length() - 1);
			ms.prepareAndSendLoginReminderMail(recipientEmail, recipientName);
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}

	
	
