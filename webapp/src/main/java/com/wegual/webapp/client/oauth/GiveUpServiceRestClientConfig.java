package com.wegual.webapp.client.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import app.wegual.common.client.oauth.AbstractRestClientConfig;

@Component
@ConfigurationProperties(prefix = "giveup-service")
public class GiveUpServiceRestClientConfig extends AbstractRestClientConfig {

	@Override
	public String getServiceId() {
		return "giveup-service";
	}
}