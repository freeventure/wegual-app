package com.wegual.webapp.client;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ClientBeans implements ApplicationContextAware {

	private static ApplicationContext theContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ClientBeans.theContext = applicationContext;

	}

	public static UserServiceClient getUserServiceClient() {
		return theContext.getBean(UserServiceClient.class);
	}

	public static BeneficiaryServiceClient getBeneficiaryServiceClient() {
		return theContext.getBean(BeneficiaryServiceClient.class);
	}

	public static PledgeServiceClient getPledgeServiceClient() {
		return theContext.getBean(PledgeServiceClient.class);
	}
	public static GiveUpServiceClient getGiveUpServiceClient() {
		return theContext.getBean(GiveUpServiceClient.class);
	}
}
