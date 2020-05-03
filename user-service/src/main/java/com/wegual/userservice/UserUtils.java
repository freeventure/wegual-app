package com.wegual.userservice;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.wegual.common.model.User;
import app.wegual.common.model.UserEmailVerifyToken;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserUtils {

	public static User userFromESDocument(Map<String, Object> source) {
		if(source == null || source.isEmpty())
			return null;
		String username = source.get("username") != null ? source.get("username").toString() : null;
		String email = source.get("email") != null ? source.get("email").toString() : null;
		if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(email)) {
			User user = new User(username, email);
			user.setId(source.get("user_id") != null ? source.get("user_id").toString() : null);
			user.setFirstName(source.get("firstName") != null ? source.get("firstName").toString() : null);
			user.setLastName(source.get("lastName") != null ? source.get("lastName").toString() : null);
			user.setPictureLink(source.get("picture_link") != null ? source.get("picture_link").toString() : null);
			return user;
		}
		return null;
	}

	public static Map<String, Object> jsonPropertiesFromUser(User user) {
		Map<String, Object> jsonMap = new HashMap<>();

		if (user != null) {
			jsonMap.put("user_id", user.getId());
			jsonMap.put("firstName", user.getFirstName());
			jsonMap.put("lastName", user.getLastName());
			jsonMap.put("email", user.getEmail());
			jsonMap.put("createdTimestamp", user.getCreatedTimestamp());
			jsonMap.put("updatedTimestamp", user.getUpdatedTimestamp());
			jsonMap.put("username", user.getUsername());
			jsonMap.put("full_name", user.getFirstName() + " " + user.getLastName());
			
			// for now this is not defined on user so put hardcoded values
			jsonMap.put("account_locked", Boolean.FALSE);
			jsonMap.put("is_active", Boolean.TRUE);
		}

		return jsonMap;
	}

	public static UserEmailVerifyToken jsonPropertiesForEmailVerify(Map<String, Object> src) {
		UserEmailVerifyToken uevt = null;
		ObjectMapper mapper = new ObjectMapper();

			try {
				// Convert object to Map
				uevt = mapper.convertValue(src, UserEmailVerifyToken.class);
			} catch (Exception ex) {
				log.error("Unable to convert user email verify JSON map to object");
			}
		return uevt;
	}

	public static User fromJsonString(String src) throws Exception {
		User user = null;
		ObjectMapper mapper = new ObjectMapper();

			try {
				// Convert object to Map
				user = mapper.readValue(src, User.class);
			} catch (Exception ex) {
				log.error("Unable to convert user email verify JSON map to object");
				throw ex;
			}
		return user;
	}
	
	public static Map<String, Object> jsonPropertiesForEmailVerify(UserEmailVerifyToken userToken) {
		Map<String, Object> jsonMap = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();

		if (userToken != null) {
			try {
				// Convert object to Map
				jsonMap = mapper.convertValue(userToken, new TypeReference<Map<String, Object>>() {
				});
			} catch (Exception ex) {
				log.error("Unable to convert user email verify token to JSON map");
			}
		}
		return jsonMap;
	}
	
}
