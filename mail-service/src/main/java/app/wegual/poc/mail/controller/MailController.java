package app.wegual.poc.mail.controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.wegual.poc.mail.service.MailService;
import app.wegual.poc.mail.service.OTPGenerator;

@RestController
@RequestMapping("/mail")
public class MailController {
	
	@Autowired
	private MailService ms;
	@Autowired
	private OTPGenerator otpGenerator;
	
	@PostMapping("/sendLoginVerificationMail")
	public String sendLoginVerificationMail() throws InvalidKeyException, NoSuchAlgorithmException {
		System.out.println("Inside Mail Controller");
		return ms.prepareAndSend("modigofast@gmail.com", otpGenerator.generateOTP(Instant.now()));
		
	}
}
