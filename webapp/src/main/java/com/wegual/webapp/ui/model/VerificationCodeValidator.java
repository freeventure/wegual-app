package com.wegual.webapp.ui.model;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class VerificationCodeValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return VerifyCode.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors err) {
		ValidationUtils.rejectIfEmpty(err, "token", "user.verify.token.empty");

		VerifyCode vc = (VerifyCode) target;

		if(vc.getToken().length() != 6)
			err.rejectValue("token", "user.verify.token.invalid");
		
		Pattern pattern = Pattern.compile("\\d+", Pattern.CASE_INSENSITIVE);
		if (!(pattern.matcher(vc.getToken()).matches())) {
			err.rejectValue("token", "user.verify.token.invalid");
		}
	}

}
