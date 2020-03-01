package app.wegual.poc.scheduler.jobs;

import java.io.IOException;
import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import app.wegual.poc.es.service.UserService;
import app.wegual.poc.scheduler.messaging.LoginReminderMessageSender;
import app.wegual.poc.es.model.User;
@Component
@DisallowConcurrentExecution
public class LoginReminderJob implements Job {

	@Autowired
	LoginReminderMessageSender lrms;
	
	public List<User> findInactiveUsers() throws IOException{
		return new UserService().findInactiveUsers();
	}
	@Override
	public  void execute(JobExecutionContext context) throws JobExecutionException {
		
		try {
			List<User> inactiveUsers = findInactiveUsers();
			lrms.sendMessage(inactiveUsers);
		}
		catch(Exception e) {
			System.out.println(e);
		}	
	}
}
