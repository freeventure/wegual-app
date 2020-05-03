package com.wegual.webapp.ui.model;

import app.wegual.common.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterAccount {

	private String firstName;
	private String lastName;
	
	private String email;
	private String username;
	
	private String password;
	private String confirmPassword;
	
	public User userFrom() {
		User user = new User();
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		return user;
	}
	
}
