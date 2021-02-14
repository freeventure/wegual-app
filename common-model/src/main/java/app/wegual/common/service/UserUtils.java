package app.wegual.common.service;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import app.wegual.common.model.Location;
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
			user.setBaseCurrency(source.get("base_currency") !=null ? Currency.getInstance((String) source.get("base_currency")) : null);
			user.setFilledDetails(source.get("filled_details") != null ? (boolean)source.get("filled_details") : false);
			Map<String,Object> locsrc = (Map<String, Object>) source.get("location");
			if(locsrc!=null) {
				Location loc = new Location();
				loc.setCity(locsrc.get("city") != null ? locsrc.get("city").toString() : null);
				loc.setState(locsrc.get("state")!= null ? locsrc.get("state").toString() : null);
				loc.setCountry(locsrc.get("country")!= null ? locsrc.get("country").toString() : null);
				user.setLocation(loc);
			}
			else {
				user.setLocation(null);
			}
			return user;
		}
		return null;
	}
	
	public static Map<String, Object> jsonPropertiesFromUser(User user) {
		Map<String, Object> jsonMap = new HashMap<>();

		if (user != null) {
			jsonMap.put("user_id", user.getId());
			jsonMap.put("first_name", user.getFirstName());
			jsonMap.put("last_name", user.getLastName());
			jsonMap.put("email", user.getEmail());
			jsonMap.put("creation_stamp", user.getCreatedTimestamp());
			jsonMap.put("last_udate_stamp", user.getUpdatedTimestamp());
			jsonMap.put("username", user.getUsername());
			jsonMap.put("full_name", user.getFirstName() + " " + user.getLastName());
			
			// for now this is not defined on user so put hardcoded values
			jsonMap.put("account_locked", Boolean.FALSE);
			jsonMap.put("is_active", Boolean.TRUE);
			jsonMap.put("filled_details", Boolean.FALSE);
		}

		return jsonMap;
	}

}
