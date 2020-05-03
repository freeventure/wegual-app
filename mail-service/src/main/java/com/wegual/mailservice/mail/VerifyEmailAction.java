package com.wegual.mailservice.mail;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import app.wegual.common.model.UserEmailVerifyToken;

@Component
public class VerifyEmailAction {
	
	@Autowired
	MailClient mc;
	
	@Autowired
	@Qualifier("executorMailSender")
	TaskExecutor te;
	
	private static class AsyncMailSender implements Runnable {
		private MailClient mc;
		private MailParameters mp;
		private String otp;
		
		
		public AsyncMailSender(MailClient mailClient, String otp, MailParameters params) {
			mc = mailClient;
			mp = params;
			this.otp = otp;
		}
		
		public void run() {
	        Context context = new Context();
	        context.setVariable("otp", otp);
	        mc.prepareAndSendHTML(context, mp.from(), mp.subject(), mp.recipient(), mp.template());	        
		}
	}	
	
	public void sendMessage(UserEmailVerifyToken uevt) {
		HashMap<String, String> mailParams = new HashMap<>();
		mailParams.put("template", "loginVerify");
		mailParams.put("subject", "Verify your account");
		mailParams.put("from", "codesuv@gmail.com");
		mailParams.put("recipient", uevt.getEmail());
		MailParameters params = new MailParameters(mailParams);
		te.execute(new AsyncMailSender(mc, uevt.getToken(), params));
	}

}
