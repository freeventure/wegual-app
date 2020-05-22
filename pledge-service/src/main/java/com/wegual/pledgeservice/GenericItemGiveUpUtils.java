package com.wegual.pledgeservice;

import java.util.HashMap;
import java.util.Map;

import app.wegual.common.model.GenericItem;
import lombok.extern.slf4j.Slf4j;

public class GenericItemGiveUpUtils {
	public static GenericItem<String> giveupGenericItemFromEsDocument(Map<String, Object> source) {
		GenericItem<String> giveup = new GenericItem<String>();
		String giveupId = source.get("giveup_id").toString();
		giveup.setId(source.get("giveup_id").toString());
		giveup.setName(source.get("giveup_name").toString());
		giveup.setPermalink("/giveup/"+giveupId);
		giveup.setPictureLink(source.get("picture_link").toString());
		return giveup;
	}
	
	public static Map<String, Object> jsonPropertiesFromGenericItemGiveUp(GenericItem<String> giveup){
		Map<String, Object> giveupmap = new HashMap<String, Object>();
		
		if(giveup!=null) {
			giveupmap.put("id", giveup.getId());
			giveupmap.put("name", giveup.getName());
			giveupmap.put("picture_link", giveup.getPictureLink());
			giveupmap.put("permalink", giveup.getPermalink());
		}
		
		return giveupmap;
	}
}
