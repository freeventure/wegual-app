package com.wegual.scheduler.client;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ClientBeans implements ApplicationContextAware {
	
	private static ApplicationContext theContext;
	
	@Autowired
	private UserServiceClient usc;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ClientBeans.theContext = applicationContext;
		
	}

	public static UserServiceClient getUserServiceClient() {
		return theContext.getBean(UserServiceClient.class);
	}	
}
