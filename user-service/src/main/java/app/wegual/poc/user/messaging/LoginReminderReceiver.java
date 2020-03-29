//package app.wegual.poc.user.messaging;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import app.wegual.poc.common.model.User;
//import app.wegual.poc.common.util.MessagingConstants;
//import app.wegual.poc.user.service.UserService;
//
//@Service
//public class LoginReminderReceiver {
//	
//	@Autowired 
//	UserService userService;
//	
//	@Autowired
//	LoginReminderMessageSender lrms;
//	
//	@RabbitListener(queues = MessagingConstants.queueNameUserServiceScheduler)
//	public void receiveMessageForUserSchedulerService(String message) throws IOException {
//		System.out.println("scheduler message received: " + message);
//		ArrayList<User> inactiveUsers =  userService.findInactiveUsers();
//		System.out.println("total inactive users: " + inactiveUsers.size());
//		String msg = "";
//		for (int i = 0; i < inactiveUsers.size(); i++) {
//			User u = inactiveUsers.get(i);
//			msg = u.getEmail() + "#" + u.getName();
//			System.out.println(msg);
//			lrms.sendMessage(msg);
//		}
//	}
//
//}
