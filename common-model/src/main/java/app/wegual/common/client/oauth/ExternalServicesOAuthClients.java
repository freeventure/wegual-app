package app.wegual.common.client.oauth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.stereotype.Repository;

@Repository
public class ExternalServicesOAuthClients {
	
	private static Map<String, OAuth2RestTemplate> restTemplates = new HashMap<>();
	public OAuth2RestTemplate restTemplate(String clientKey) {

		synchronized (restTemplates) {
			OAuth2RestTemplate restTemplate = restTemplates.get(clientKey);

			if (restTemplate == null) {
				AbstractRestClientConfig config = RestClientConfigFactory.getInstance().getConfigFor(clientKey);
				ClientCredentialsResourceDetails details = config.fromMe();
				restTemplate = new OAuth2RestTemplate(details, new DefaultOAuth2ClientContext());
				restTemplates.put(clientKey, restTemplate);
			}

			return restTemplate;
		}
	}
}
