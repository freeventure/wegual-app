package app.wegual.poc.scheduler.clients;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import app.wegual.poc.scheduler.clients.oauth.ExternalServicesOAuthClients;

@Component
public class ClientBeans implements ApplicationContextAware{

	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		ClientBeans.applicationContext = applicationContext;
	}

	public static UserServiceClient getUserServiceClient() {
		return applicationContext.getBean(UserServiceClient.class);
	}
	
	public static ExternalServicesOAuthClients getExternalServicesOAuthClients() {
		return applicationContext.getBean(ExternalServicesOAuthClients.class);
	}

}
