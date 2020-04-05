package com.wegual.webapp;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wegual.webapp.message.LoginTimelineItemBuilder;
import com.wegual.webapp.message.UserActionsMessageSender;

import app.wegual.common.asynch.SenderRunnable;
import app.wegual.common.model.UserTimelineItem;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthSuccessHandler implements ApplicationListener<AuthenticationSuccessEvent> {
	
	@Autowired
	@Qualifier("executorMessageSender")
	TaskExecutor te;
	
	@Autowired
	private UserActionsMessageSender uams;

	@SuppressWarnings("unchecked")
	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		AccessToken token = null;
		KeycloakPrincipal<KeycloakSecurityContext> kp = null;
		Object p = event.getAuthentication().getPrincipal();
		if(p instanceof KeycloakPrincipal) {
			kp = (KeycloakPrincipal<KeycloakSecurityContext>)p;
			token = kp.getKeycloakSecurityContext().getToken();
			log.info(token.getName());
			log.info(kp.getKeycloakSecurityContext().getIdToken().getSubject());
			
		}
		log.info("Login success");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
		if(request != null)
		{
			log.info(request.getRemoteAddr());
			if(token != null)
			{
				UserTimelineItem uti = new LoginTimelineItemBuilder()
						.userId(kp.getKeycloakSecurityContext().getIdToken().getSubject())
						.time(System.currentTimeMillis())
						.fromIp(request.getRemoteAddr())
						.userName(token.getName())
						.build();
				sendMessageAsynch(uti);
			}
		}
	}

	protected void sendMessageAsynch(UserTimelineItem uti) {
		te.execute(new SenderRunnable<UserActionsMessageSender, UserTimelineItem>(uams, uti));
	}

}
