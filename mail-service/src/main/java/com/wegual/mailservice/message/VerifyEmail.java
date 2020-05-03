package com.wegual.mailservice.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wegual.mailservice.mail.VerifyEmailAction;

import app.wegual.common.model.UserEmailVerifyToken;

@Component
public class VerifyEmail {
	
	@Autowired
	VerifyEmailAction vea;
	
	@RabbitListener(queues = "mail-verification")
    public void receiveObjectMessage(UserEmailVerifyToken uevt) {
		vea.sendMessage(uevt);
	}
	
}
