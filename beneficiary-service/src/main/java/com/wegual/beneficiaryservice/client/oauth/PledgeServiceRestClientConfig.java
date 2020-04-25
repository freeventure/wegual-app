package com.wegual.beneficiaryservice.client.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import app.wegual.common.client.oauth.AbstractRestClientConfig;

//@Component
//@ConfigurationProperties(prefix = "pledge-service")
public class PledgeServiceRestClientConfig extends AbstractRestClientConfig {

	@Override
	public String getServiceId() {
		
		return "pledge-service";
	}

}
