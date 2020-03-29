package app.wegual.poc.scheduler.clients.oauth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class ExternalServicesOAuthClients {
	
	@Value("${security.oauth2.client.access-token-uri}")
    private String accessTokenUri;
	
	private static Map<String, OAuth2RestTemplate> restTemplates = new HashMap<>();
	public OAuth2RestTemplate restTemplate(String clientKey) {

		synchronized (restTemplates) {
			OAuth2RestTemplate restTemplate = restTemplates.get(clientKey);

			if (restTemplate == null) {
				AbstractRestClientConfig config = RestClientConfigFactory.getInstance().getConfigFor(clientKey);
				ClientCredentialsResourceDetails details = config.fromMe();
				details.setAccessTokenUri(accessTokenUri);
				restTemplate = new OAuth2RestTemplate(details, new DefaultOAuth2ClientContext());
				restTemplates.put(clientKey, restTemplate);
			}

			return restTemplate;
		}
	}
}
