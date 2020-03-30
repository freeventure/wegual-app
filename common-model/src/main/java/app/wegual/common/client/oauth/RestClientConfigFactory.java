package app.wegual.common.client.oauth;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		log.info("Registered oauth2 client id: " + clientId);
		
	}
	
	public AbstractRestClientConfig getConfigFor(String clientId) {
		if(!clientConfigs.containsKey(clientId))
			throw new IllegalStateException("Client id: " + clientId + " is not registered yet");
		return clientConfigs.get(clientId);
	}
}
