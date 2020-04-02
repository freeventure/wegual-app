package com.wegual.webapp;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthSuccessHandler implements ApplicationListener<AuthenticationSuccessEvent> {

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		log.info("Login success");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
		if(request != null)
		log.info(request.getRemoteAddr());
	}

}
