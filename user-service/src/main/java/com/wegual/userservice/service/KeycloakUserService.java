package com.wegual.userservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import app.wegual.common.client.CommonBeans;
import app.wegual.common.model.User;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KeycloakUserService {

	@Value("${rest.security.issuer-uri}")
    private String keycloakBaseUrl;
	
	private RestTemplate template;
	
	public User getUserPrfoileData(String username) {
		User user = null;
		OAuth2AccessToken token = null;
		try {
		OAuth2RestTemplate ort = CommonBeans.getExternalServicesOAuthClients().restTemplate("user-manager");
		if(ort != null)
		{
			token =  ort.getAccessToken();
			log.info("Created token");
			log.info("Value: " + token.getValue());
			
			user = this.getUserFromKeycloak(token.getValue(), username);
			if(user == null)
				throw new IllegalStateException("Unable to get user from identity store");
			return user;
		} else throw new IllegalStateException("Unable to get auth token from authorization server");

		} catch (Exception ex) {
			log.error("Error getting user for username: " + username, ex);
			throw ex;
		}		
	}
	
	private User getUserFromKeycloak(String authToken, String username) {
		final HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + authToken);

		// Create a new HttpEntity
		final HttpEntity<String> entity = new HttpEntity<String>(headers);

		template = new RestTemplateBuilder().build();
		// Execute the method writing your HttpEntity to the request
		String keycloakUrl = keycloakBaseUrl + "/admin/realms/wegual/users?username=" + username;
		ResponseEntity<User[]> response = template.exchange(keycloakUrl, HttpMethod.GET, entity, User[].class);
		User[] usersFound = response.getBody();
		if(usersFound != null && usersFound.length > 0)
			return usersFound[0];
		return null;
	}

}
