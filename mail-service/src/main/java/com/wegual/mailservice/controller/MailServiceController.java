package com.wegual.mailservice.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wegual.mailservice.mail.MailClient;
import com.wegual.mailservice.mail.VerifyEmailAction;

import app.wegual.common.asynch.SenderRunnable;

@RestController
public class MailServiceController {

	@Autowired
	MailClient mc;
	
	@Autowired
	@Qualifier("executorMailSender")
	TaskExecutor tems;
	
	@PostMapping("/beneficiary/email-verify-html")
	ResponseEntity<String> emailVerifyHTML(@RequestBody HashMap<String, String> params) {
		
		try {

			VerifyEmailAction vea = new VerifyEmailAction(mc, params);
			tems.execute(new SenderRunnable<VerifyEmailAction, HashMap<String, String>>(vea, params));
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
		}
	    return new ResponseEntity<>("OK", HttpStatus.OK);
	}
	
	protected void sendMessageAsynch() {
		
	}
	
}
