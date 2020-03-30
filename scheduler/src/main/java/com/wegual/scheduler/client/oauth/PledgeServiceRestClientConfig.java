package com.wegual.scheduler.client.oauth;

import app.wegual.common.client.oauth.AbstractRestClientConfig;

//@Component
//@ConfigurationProperties(prefix = "pledge-service")
public class PledgeServiceRestClientConfig extends AbstractRestClientConfig {

	@Override
	public String getServiceId() {
		
		return "pledge-service";
	}

}
