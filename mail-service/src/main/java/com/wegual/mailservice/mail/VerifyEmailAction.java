package com.wegual.mailservice.mail;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HashMap;

import org.thymeleaf.context.Context;

import app.wegual.common.asynch.MessageSender;
import app.wegual.common.util.OTPGenerator;

public class VerifyEmailAction implements MessageSender {
	
	private MailClient mc;
	private MailParameters mp;
	
	public VerifyEmailAction(MailClient client, HashMap<String, String> params) {
		mc = client;
		mp = new MailParameters(params);
		mp.validate();
	}

	
	@Override
	public void sendMessage(Object object) {
        Context context = new Context();
	    String otp = "";
		try {
			otp = OTPGenerator.generateOTP(Instant.now());
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        context.setVariable("otp", otp);
        mc.prepareAndSendHTML(context, mp.from(), mp.subject(), mp.recipient(), mp.template());
		
	}

}
