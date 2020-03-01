package app.wegual.poc.mail.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentBuilder {
	
	@Autowired
	private TemplateEngine templateEngine;
 
    public String build(String otp) {
        Context context = new Context();
        context.setVariable("otp", otp);
        return templateEngine.process("verifyLogin", context);
    }
}
