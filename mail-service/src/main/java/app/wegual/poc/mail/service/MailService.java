package app.wegual.poc.mail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    
	@Autowired
    private JavaMailSender javaMailSender;
	@Autowired
	private MailContentBuilder mcb;
 
    public String prepareAndSend(String recipient, String otp) {
    	MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("wegual.test@gmail.com");
            messageHelper.setTo(recipient);
            messageHelper.setSubject("Verification Mail");
            String content = mcb.build(otp);
            messageHelper.setText(content, true);
        };
        try {
            javaMailSender.send(messagePreparator);
            return "OK";
        } catch (MailException e) {
        	e.printStackTrace();
        	return "Error";
        }
    }
}