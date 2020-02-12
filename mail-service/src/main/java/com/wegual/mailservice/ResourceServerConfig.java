package com.wegual.mailservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Value("${security.oauth2.resource.token-info-uri}")
    private String tokenInfoURI;
	
	@Value("${security.oauth2.client.client-id}")
    private String clientId;

	@Value("${security.oauth2.client.client-secret}")
    private String clientSecret;
	
	@Bean
    public RemoteTokenServices remoteTokenServices() {
        RemoteTokenServices s = new RemoteTokenServices();
        
        s.setCheckTokenEndpointUrl(tokenInfoURI);
        s.setClientId(clientId);
        s.setClientSecret(clientSecret);
        return s;
    }

}
