package com.wegual.scheduler.jobs.predefined;

import java.util.ArrayList;
import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import com.wegual.scheduler.client.ClientBeans;
import com.wegual.scheduler.client.UserServiceClient;

import app.wegual.common.client.CommonBeans;
import app.wegual.common.model.User;

@Component
@DisallowConcurrentExecution
public class LoginReminderJob implements Job {

	@Override
	public void execute(JobExecutionContext context) {
		OAuth2AccessToken token = null;
		try {
		OAuth2RestTemplate ort = CommonBeans.getExternalServicesOAuthClients().restTemplate("user-service");
		if(ort != null)
		{
			token =  ort.getAccessToken();
			System.out.println("Created token");
			System.out.println("Value: " + token.getValue());
		}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		List<User> users = getUsersNotLoggedIn("Bearer " + token.getValue());
		if (users != null) {
			for (User user : users) {
				System.out.println("Found user id: " + user.getId());
			}
		}
		System.out.println("Hey, I ran from Quartz");
	}
	
    // how to do this for thousands/millions? 
	public List<User> getUsersNotLoggedIn(String bearerToken) {
		User user = null;
		UserServiceClient usc = ClientBeans.getUserServiceClient();
		List<User> users = new ArrayList<User>();
		for(String userId : usc.getUserLoginReminders(bearerToken)) {
			user = new User();
			user.setId(userId);
			users.add(user);
		}
		return users;
	}
}
