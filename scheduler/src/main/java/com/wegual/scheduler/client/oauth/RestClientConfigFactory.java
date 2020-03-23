package com.wegual.scheduler.client.oauth;

import java.util.HashMap;
import java.util.Map;

public class RestClientConfigFactory {

	private static RestClientConfigFactory INSTANCE = new RestClientConfigFactory();
	
	private Map<String, AbstractRestClientConfig> clientConfigs = new HashMap<>();
	private RestClientConfigFactory() {
		
	}
	
	public static RestClientConfigFactory getInstance() {
		return INSTANCE;
	}
	
	public void register(String clientId, AbstractRestClientConfig value) {
		clientConfigs.put(clientId, value);
	}
	
	public AbstractRestClientConfig getConfigFor(String clientId) {
		if(!clientConfigs.containsKey(clientId))
			throw new IllegalStateException("Client id: " + clientId + " is not registered yet");
		return clientConfigs.get(clientId);
	}
}
