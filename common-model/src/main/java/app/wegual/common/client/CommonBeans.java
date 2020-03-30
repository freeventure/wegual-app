package app.wegual.common.client;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import app.wegual.common.client.oauth.ExternalServicesOAuthClients;

@Component
public class CommonBeans implements ApplicationContextAware {
	
	private static ApplicationContext theContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		CommonBeans.theContext = applicationContext;
		
	}

	public static ExternalServicesOAuthClients getExternalServicesOAuthClients() {
		return theContext.getBean(ExternalServicesOAuthClients.class);
	}
}
