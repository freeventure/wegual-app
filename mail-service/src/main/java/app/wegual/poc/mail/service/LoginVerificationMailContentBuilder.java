package app.wegual.poc.mail.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class LoginVerificationMailContentBuilder {
	
	@Autowired
	private TemplateEngine templateEngine;
 
    public String build(String recipientName) {
        Context context = new Context();
        context.setVariable("recipientName", recipientName);
        return templateEngine.process("reminderLogin", context);
    }
}
