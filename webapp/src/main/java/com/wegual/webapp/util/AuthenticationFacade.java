package com.wegual.webapp.util;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {

	Authentication getAuthentication();
	String getUserLoginName();
	String getUserId();
	String getUserFullName();
}
