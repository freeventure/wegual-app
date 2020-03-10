package com.wegual.scheduler.jobs.predefined;

import java.util.ArrayList;
import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import com.wegual.scheduler.client.ClientBeans;
import com.wegual.scheduler.client.UserServiceClient;

import app.wegual.common.model.User;

@Component
@DisallowConcurrentExecution
public class LoginReminderJob implements Job {
	
//	@Autowired
//	private DiscoveryClient dc;
	
	public LoginReminderJob() {
		System.out.println("My Instance is: " + this.toString());
	}

//	@Override
//	public void execute(JobExecutionContext context) {
//		System.out.println("In Execute, my Instance is: " + this.toString());
//		if(dc != null) {
//			List<ServiceInstance> services = dc.getInstances("scheduler-service");
//			if (services != null) {
//				for (ServiceInstance service : services) {
//					System.out.println("Found service: " + service.getInstanceId());
//					System.out.println("Host: " + service.getHost());
//					System.out.println("Port: " + service.getPort());
//					System.out.println("============================");
//				}
//			} else {
//				System.out.println("DC is Null here");
//			}
//		}
//		List<User> users = getUsersNotLoggedIn();
//		if (users != null) {
//			for (User user : users) {
//
//			}
//		}
//		System.out.println("Hey, I ran from Quartz");
//	}

	@Override
	public void execute(JobExecutionContext context) {
		System.out.println("In Execute, my Instance is: " + this.toString());
		List<User> users = getUsersNotLoggedIn();
		if (users != null) {
			for (User user : users) {
				System.out.println("Found user id: " + user.getId());
			}
		}
		System.out.println("Hey, I ran from Quartz");
	}
	
	// how to do this for thousands/millions? 
	public List<User> getUsersNotLoggedIn() {
		User user = null;
		UserServiceClient usc = ClientBeans.getUserServiceClient();
		List<User> users = new ArrayList<User>();
		for(String userId : usc.getUserLoginReminders()) {
			user = new User();
			user.setId(Long.valueOf(userId));
			users.add(user);
		}
		return users;
	}
}
