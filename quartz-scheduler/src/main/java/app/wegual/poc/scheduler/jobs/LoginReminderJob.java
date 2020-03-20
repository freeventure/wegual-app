package app.wegual.poc.scheduler.jobs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.wegual.poc.scheduler.messaging.LoginReminderMessageSender;
//import app.wegual.poc.user.service.UserService;
//import app.wegual.poc.es.model.User;
@Component
@DisallowConcurrentExecution
public class LoginReminderJob implements Job {

	@Autowired
	LoginReminderMessageSender lrms;

	@Override
	public  void execute(JobExecutionContext context) throws JobExecutionException {
		
		try {
			System.out.println("Inside our first job");
			lrms.sendMessage("inactiveUsers");
		}
		catch(Exception e) {
			System.out.println(e);
		}	
	}
}
