package com.wegual.mailservice.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@RefreshScope
@Component
public class MailClient {
 
    private JavaMailSender mailSender;
    
    
    @Autowired
    MailContentBuilder mcb;
 
    @Autowired
    public MailClient(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
 
	public void prepareAndSendHTML(Context ctx, String from, String subject, String recipient, String template) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom(from);
			messageHelper.setTo(recipient);
			messageHelper.setSubject(subject);
			String content = mcb.build(template, ctx);
			messageHelper.setText(content, true); };
		try {
			mailSender.send(messagePreparator);
		} catch (MailException e) {
			// runtime exception; compiler will not force you to handle it
		}
	}
}