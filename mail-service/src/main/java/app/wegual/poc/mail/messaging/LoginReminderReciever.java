package app.wegual.poc.mail.messaging;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.wegual.poc.common.util.MessagingConstants;
import app.wegual.poc.es.model.User;
import app.wegual.poc.mail.service.MailService;

@Service
public class LoginReminderReciever {
	
	@Autowired 
	MailService ms;
	
	@RabbitListener(queues = MessagingConstants.queueNameLoginReminderTimeline)
	public void receiveMessageForBeneficiaryTimeline(ArrayList<User> inactiveUsers) throws IOException {
		System.out.println("received");
		for (int i = 0; i < inactiveUsers.size(); i++) {
			User u = inactiveUsers.get(i);
			ms.prepareAndSendLoginReminderMail(u.getEmail(), u.getName());
		}
	}
}

	
	
