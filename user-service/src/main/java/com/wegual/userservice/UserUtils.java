package com.wegual.userservice;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.wegual.common.model.GenericItem;
import app.wegual.common.model.Location;
import app.wegual.common.model.User;
import app.wegual.common.model.UserEmailVerifyToken;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserUtils {
	
	@Autowired
	private static ElasticSearchConfig esConfig;

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
	
	public static GenericItem<String> getUserGenericItem(String userId) {
		GenericItem<String> user = new GenericItem<String>();
		try {
			SearchRequest searchRequest = new SearchRequest("user_idx");
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
			sourceBuilder.query(QueryBuilders.termQuery("user_id", userId));
			searchRequest.source(sourceBuilder);
			
			RestHighLevelClient client = esConfig.getElastcsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			
			User u = null;
			if(searchResponse.getHits().getTotalHits().value>0L) {
				for(SearchHit hit : searchResponse.getHits()) {
					u = new ObjectMapper().readValue(hit.getSourceAsString(), User.class);
				}
			}
			user.setId(u.getId());
			user.setName(u.getFirstName()+" "+u.getLastName());
			user.setPictureLink(u.getPictureLink());
			user.setPermalink("/user/"+u.getId());
			return user;
			
		} catch (Exception e) {
			log.info("Users not found", e);
		}
		return user;
	}
	public static GenericItem<String> userToGenericItem(User user){
		GenericItem<String> item = new GenericItem<String>(user.getId(), user.getFirstName() + " " + user.getLastName(), 
				"/user/" + user.getId(), user.getPictureLink());
		return item;
	}
	
	public static GenericItem<String> userToGenericItem(Map<String, Object> user){
		//InstanceInfo instance = discoveryClient.getNextServerFromEureka("user-service", false);
		GenericItem<String> item = new GenericItem<String>((String)user.get("user_id"), (String)user.get("full_name"), 
				(String)("/user/" + user.get("user_id")), (String)(user.get("picture_link")));
		return item;
	}
	
}
