package com.wegual.pledgeservice;

import java.util.HashMap;
import java.util.Map;

import app.wegual.common.model.GenericItem;
import lombok.extern.slf4j.Slf4j;

public class GenericItemUserUtils {
	
	public static GenericItem<String> userGenericItemFromEsDocument(Map<String, Object> source) {
		GenericItem<String> user = new GenericItem<String>();
		String userId = source.get("user_id").toString();
		user.setId(source.get("user_id").toString());
		user.setName(source.get("full_name").toString());
		user.setPictureLink(source.get("picture_link").toString());
		user.setPermalink("/users/"+userId);
		return user;
	}
	
	public static Map<String, Object> jsonPropertiesFromGenericItemUser(GenericItem<String> user){
		Map<String, Object> usermap = new HashMap<String, Object>();
		
		if(user!=null) {
			usermap.put("id", user.getId());
			usermap.put("name", user.getName());
			usermap.put("picture_link", user.getPictureLink());
			usermap.put("permalink", user.getPermalink());
		}
		
		return usermap;
	}
}
