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

@RestController
public class MailServiceController {

	@Autowired
	MailClient mc;
	
	@Autowired
	@Qualifier("executorMailSender")
	TaskExecutor tems;
	
	@PostMapping("/mail-service/email-verify-html")
	ResponseEntity<String> emailVerifyHTML(@RequestBody HashMap<String, String> params) {
		
	    return new ResponseEntity<>("OK", HttpStatus.OK);
	}
}
