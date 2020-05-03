package com.wegual.userservice.service;

import java.util.Arrays;

import javax.ws.rs.core.Response;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
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

	@Value("${keycloak.realm}")
    private String realm;

	@Value("${keycloak.client-id}")
    private String clientId;

	@Value("${keycloak.client-secret}")
    private String clientSecret;

	private RestTemplate template;
	
	public void activateAccount(String userId) {
		try {
			
			Keycloak keycloak = KeycloakBuilder.builder() //
	                .serverUrl(keycloakBaseUrl) //
	                .realm(realm) //
	                .grantType(OAuth2Constants.CLIENT_CREDENTIALS) //
	                .clientId(clientId) //
	                .clientSecret(clientSecret) //
	                .build();
	        // Get realm
	        RealmResource realmResource = keycloak.realm(realm);
	        UsersResource usersRessource = realmResource.users();
	        UserResource ures = usersRessource.get(userId);
	        if(ures != null) {
	        	log.info("Found user account with id: " + userId);
	        	UserRepresentation ur = ures.toRepresentation();
	        	ur.setEnabled(true);
	        	ures.update(ur);
	        	log.info("User account activated with id: " + userId);
	        }
			
		} catch (Exception ex) {
			log.error("User account is not activated: " + userId);
		}
		
	}
	
	public String createInactiveUserAccount(User user, String password) {
		try {
			
			Keycloak keycloak = KeycloakBuilder.builder() //
	                .serverUrl(keycloakBaseUrl) //
	                .realm(realm) //
	                .grantType(OAuth2Constants.CLIENT_CREDENTIALS) //
	                .clientId(clientId) //
	                .clientSecret(clientSecret) //
	                .build();			
			
			// Define user
	        UserRepresentation userRep = new UserRepresentation();
	        userRep.setEnabled(false);
	        userRep.setUsername(user.getUsername());
	        userRep.setFirstName(user.getFirstName());
	        userRep.setLastName(user.getLastName());
	        userRep.setEmail(user.getEmail());
	        

	        // Get realm
	        RealmResource realmResource = keycloak.realm(realm);
	        UsersResource usersRessource = realmResource.users();

	        // Create user (requires manage-users role)
	        Response response = usersRessource.create(userRep);
	        log.info("Repsonse: " + response.getStatus());
	        log.info("Response status info: " + response.getStatusInfo());
	        
	        log.info(response.getLocation().toString());
	        
	        String userId = CreatedResponseUtil.getCreatedId(response);

	        log.info("User created with userId: " + userId);

	        // Define password credential
	        CredentialRepresentation passwordCred = new CredentialRepresentation();
	        passwordCred.setTemporary(false);
	        passwordCred.setType(CredentialRepresentation.PASSWORD);
	        passwordCred.setValue(password);

	        UserResource userResource = usersRessource.get(userId);

	        // Set password credential
	        userResource.resetPassword(passwordCred);
	        
	        // Get realm role "tester" (requires view-realm role)
	        RoleRepresentation testerRealmRole = realmResource.roles().get("wegual_user").toRepresentation();
	        // Assign realm role to user
	        userResource.roles().realmLevel().add(Arrays.asList(testerRealmRole));
	        return userId;
		} catch (Exception ex) {
			log.error("Error creating Keycloak user account for username: ", ex);
			return "Unknown";
		}		
	}
	
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
