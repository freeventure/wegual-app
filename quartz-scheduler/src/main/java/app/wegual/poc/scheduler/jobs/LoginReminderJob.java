package app.wegual.poc.scheduler.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import app.wegual.poc.scheduler.clients.ClientBeans;
import app.wegual.poc.scheduler.clients.UserServiceClient;

@Component
@DisallowConcurrentExecution
public class LoginReminderJob implements Job {

	@Override
	public  void execute(JobExecutionContext context) throws JobExecutionException {
		
			System.out.println("Inside our first job");
			OAuth2AccessToken token = null;
			try {
				OAuth2RestTemplate ort = ClientBeans.getExternalServicesOAuthClients().restTemplate("user-service");
				if(ort != null)
				{
					token =  ort.getAccessToken();
					System.out.println("total scopes " + token.getScope().size());
					System.out.println("Created token");
					System.out.println("Value: " + token.getValue());
					String bearerToken = "Bearer " + token;
					UserServiceClient usc = ClientBeans.getUserServiceClient();
					usc.findInactiveUsers(bearerToken);
				}
				
			}
			catch(Exception e) {
				System.out.println(e);
			}	
	}
}
