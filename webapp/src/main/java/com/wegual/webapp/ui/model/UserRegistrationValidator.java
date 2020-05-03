package com.wegual.webapp.ui.model;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserRegistrationValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return RegisterAccount.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors err) {
		ValidationUtils.rejectIfEmpty(err, "firstName", "user.first.name.empty");
		ValidationUtils.rejectIfEmpty(err, "lastName", "user.last.name.empty");
		ValidationUtils.rejectIfEmpty(err, "username", "user.username.empty");
		ValidationUtils.rejectIfEmpty(err, "email", "user.email.empty");
		ValidationUtils.rejectIfEmpty(err, "password", "user.password.empty");

		RegisterAccount user = (RegisterAccount) target;

		if(user.getPassword().length() < 8)
			err.rejectValue("password", "user.password.length");
		
		if(!StringUtils.equals(user.getPassword(), user.getConfirmPassword()))
			err.rejectValue("confirmPassword", "user.password.mismatch");
		
		Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		if (!(pattern.matcher(user.getEmail()).matches())) {
			err.rejectValue("email", "user.email.invalid");
		}
	}

}
