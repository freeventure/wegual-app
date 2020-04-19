package com.wegual.userservice;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import app.wegual.common.model.User;

public class UserUtils {

	public static User userFromESDocument(Map<String, Object> source) {
		if(source == null || source.isEmpty())
			return null;
		String username = source.get("username") != null ? source.get("username").toString() : null;
		String email = source.get("email") != null ? source.get("email").toString() : null;
		if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(email)) {
			User user = new User(username, email);
			user.setId(source.get("user_id") != null ? source.get("user_id").toString() : null);
			user.setFirstName(source.get("first_name") != null ? source.get("first_name").toString() : null);
			user.setLastName(source.get("last_name") != null ? source.get("last_name").toString() : null);
			user.setPictureLink(source.get("picture_link") != null ? source.get("picture_link").toString() : null);
			return user;
		}
		return null;
	}
}
