package com.wegual.webapp.util;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class KeycloakAuthenticationFacade implements AuthenticationFacade {
	
	@Override
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	@Override
	public String getUserLoginName() {
		Authentication auth = null;
		String username;
		auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null)
			return null;
		if (auth.getPrincipal() instanceof UserDetails) {
			username = ((UserDetails) auth.getPrincipal()).getUsername();
		} else {
			username = auth.getPrincipal().toString();
		}
		return username;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getUserId() {
		KeycloakPrincipal<KeycloakSecurityContext> kp = null;
		Authentication auth = null;
		auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null)
			return null;
		Object p = auth.getPrincipal();
		if (p instanceof KeycloakPrincipal) {
			kp = (KeycloakPrincipal<KeycloakSecurityContext>) p;
			return kp.getKeycloakSecurityContext().getIdToken().getSubject();
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getUserFullName() {
		AccessToken token = null;
		KeycloakPrincipal<KeycloakSecurityContext> kp = null;
		Authentication auth = null;
		auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null)
			return null;
		Object p = auth.getPrincipal();
		if (p instanceof KeycloakPrincipal) {
			kp = (KeycloakPrincipal<KeycloakSecurityContext>) p;
			token = kp.getKeycloakSecurityContext().getToken();
			return token.getName();
		}
		return "";
	}

}
