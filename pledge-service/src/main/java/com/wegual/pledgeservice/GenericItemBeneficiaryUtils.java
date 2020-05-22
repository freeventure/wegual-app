package com.wegual.pledgeservice;

import java.util.HashMap;
import java.util.Map;

import app.wegual.common.model.GenericItem;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenericItemBeneficiaryUtils {

	public static GenericItem beneficiaryGenericItemFromEsDocument(Map<String, Object> source) {
		GenericItem beneficiary = new GenericItem();
		String beneficiaryId = source.get("beneficiary_id").toString();
		beneficiary.setId(source.get("beneficiary_id").toString());
		beneficiary.setName(source.get("beneficiary_name").toString());
		beneficiary.setPermalink("/beneficiary/"+beneficiaryId);
		beneficiary.setPictureLink(source.get("picture_link").toString());
		return beneficiary;
	}
	
	public static Map<String, Object> jsonPropertiesFromGenericItemBeneficiary(GenericItem beneficiary){
		Map<String, Object> beneficiarymap = new HashMap<String, Object>();
		
		if(beneficiary!=null) {
			beneficiarymap.put("id", beneficiary.getId());
			beneficiarymap.put("name", beneficiary.getName());
			beneficiarymap.put("picture_link", beneficiary.getPictureLink());
			beneficiarymap.put("permalink", beneficiary.getPermalink());
		}
		
		return beneficiarymap;
	}
}
