package com.wegual.scheduler.client.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


//user-service.client-id=scheduler-service
//user-service.client-secret=c8d90a0d-335d-4d93-8e7d-6c65b08fee09
//user-service.scope=user-service-write
//user-service.grant-type=client_credentials

@Component
@ConfigurationProperties(prefix = "user-service")
public class UserServiceRestClientConfig extends AbstractRestClientConfig {

	@Override
	public String getServiceId() {
		return "user-service";
	}
}
