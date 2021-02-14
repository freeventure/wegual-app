package com.wegual.userservice.service;

import java.io.IOException;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wegual.userservice.ElasticSearchConfig;
import com.wegual.userservice.UserUtils;
import com.wegual.userservice.message.UserActionsAsynchMessageSender;

import app.wegual.common.asynch.SenderRunnable;
import app.wegual.common.model.GiveUpLike;
import app.wegual.common.model.TokenStatus;
import app.wegual.common.model.TwitterOauthTokenPersist;
import app.wegual.common.model.User;
import app.wegual.common.model.UserDetails;
import app.wegual.common.model.UserEmailVerifyToken;
import app.wegual.common.model.UserTimelineItem;
import app.wegual.common.util.ESIndices;
import app.wegual.common.util.OTPGenerator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserActionsService {	

	@Autowired
	@Qualifier("executorMessageSender")
	TaskExecutor te;
	
	@Autowired
	KeycloakUserService kus;
	
	@Autowired
	private ElasticSearchConfig esConfig;
	
	@Autowired
	private UserActionsAsynchMessageSender uaam;
	
	public void followUser(String userId, String actorId) {
		
	}
	
	public void unfollowUser(String userId, String actorId) {
		
	}
	
	public String sendVerificationToken(String secret, User user) throws Exception {
		UserEmailVerifyToken uevt = UserEmailVerifyToken.with(user.getEmail());
		user.setCreatedTimestamp(System.currentTimeMillis());
		user.setAccountLocked(true);
		user.setActive(false);
		user.setEmailVerifyPending(true);
		
		byte[] decodedBytes = Base64.getDecoder().decode(secret);
		String password = new String(decodedBytes);
		
		String id = kus.createInactiveUserAccount(user, password);
		log.info("User id from keycloak is " + id);
		uevt.setUserId(id);
		createTokenEntry(uevt);
		sendMessageAsynch(uevt);
		return id;
	}

//	public void verifyToken(String token, String email) {
//		UserEmailVerifyToken uevt = getVerifyTokenDocument(email);
//		if(uevt == null)
//			throw new IllegalStateException("token not found");
//		if(!StringUtils.equals(token, uevt.getToken()))
//			throw new IllegalStateException("Invalid token");
//		deleteEmailToken(email);
//		
//	}

//	protected UserEmailVerifyToken getVerifyTokenDocument(String email) {
//		UserEmailVerifyToken uevt = null;
//		SearchRequest searchRequest = new SearchRequest(USER_TOKENS_INDEX);
//		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
//		sourceBuilder.query(QueryBuilders.termQuery("email", email)).size(1);
//		searchRequest.source(sourceBuilder);
//		
//		try {
//			RestHighLevelClient client = esConfig.getElastcsearchClient();
//			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//			if(searchResponse.getHits().getTotalHits().value > 0L)
//			{
//				Map<String, Object> src = null;
//				for (SearchHit hit: searchResponse.getHits()) {
//					src = hit.getSourceAsMap();
//					uevt = UserUtils.jsonPropertiesForEmailVerify(src);
//					return uevt;
//				}
//			}
//			else
//				return null;
//		} catch (Exception e) {
//			log.error("Error getting emial verification document for: " + email , e);
//		}
//		return null;
//	}

	public TokenStatus verifyTokenById(String id, String token) {
		//UserEmailVerifyToken uevt = null;
		GetRequest getRequest = new GetRequest(ESIndices.USER_TOKENS_INDEX, id);
		log.info("Verifying token %s for id %s", token, id);
		try {
			RestHighLevelClient client = esConfig.getElastcsearchClient();
			GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
			if (getResponse.isExists()) {
			    Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
			    try {
			    	String tokenFound = sourceAsMap.get("token").toString();
			    	log.info("token found:" + tokenFound);
			    	if(StringUtils.equals(token, tokenFound))
			    	{
			    		kus.activateAccount(id);
			    		deleteEmailToken(id);
			    		return TokenStatus.VERIFIED;
			    	}
			    } catch (Exception ex) {
			    	log.error("" + ex);
			    	return TokenStatus.ERROR;
			    }
			} else {
				return TokenStatus.NOT_FOUND;
			}
		} catch (Exception e) {
			log.error("Error getting email verification document by id: " + id , e);
		}
		return TokenStatus.ERROR;
	}

	private void deleteEmailToken(String userId) {
		
		DeleteByQueryRequest request = new DeleteByQueryRequest(ESIndices.USER_TOKENS_INDEX); 
		
		TermQueryBuilder termQuery = QueryBuilders.termQuery("user_id", userId);
				
		request.setQuery(termQuery);
		request.setMaxDocs(1);
		request.setBatchSize(1);
		request.setRefresh(true);
		
		try {
			BulkByScrollResponse bulkResponse = esConfig.getElastcsearchClient().deleteByQuery(request, RequestOptions.DEFAULT);
			log.info("bulk response for delete: " + bulkResponse.getDeleted());
			if(bulkResponse.getDeleted() != 1)
				throw new IllegalStateException("exactly one document was not deleted");
		} catch (IOException e) {
			
			log.error("Unable to delete token email verification document by query: " + userId, e);
		}
		
	}
	
	
	private void createTokenEntry(UserEmailVerifyToken uevt) throws Exception {
		Map<String, Object> jsonMap = UserUtils.jsonPropertiesForEmailVerify(uevt);
		
		String token = OTPGenerator.generateOTP(Instant.now());
		jsonMap.put("token", token);
		// this is important, as it will be used to send the OTP vial mail service.
		uevt.setToken(token);
		
		// generate email verification token with user id 
		IndexRequest indexRequest = new IndexRequest(ESIndices.USER_TOKENS_INDEX).id(uevt.getUserId()).source(jsonMap);
		indexRequest.opType(DocWriteRequest.OpType.INDEX); 
		IndexResponse indexResponse = esConfig.getElastcsearchClient().index(indexRequest, RequestOptions.DEFAULT);
		if (indexResponse.getResult() != DocWriteResponse.Result.CREATED)
			throw new IllegalStateException("email verification token document was not created for: " + uevt.getEmail());
		else
			log.info("User document created successfully for id: " + uevt.getEmail());
	}
	
	public void saveTwitterRequestToken(String value, String secret) {
		HashMap<String, String> requestToken = new HashMap<String, String>();
		requestToken.put("value", value);
		requestToken.put("secret", secret);
		IndexRequest indexRequest = new IndexRequest(ESIndices.TWITTER_REQUEST_TOKEN_INDEX).id(value).source(requestToken);
		indexRequest.opType(DocWriteRequest.OpType.INDEX); 
		IndexResponse indexResponse = null;
		try {
			indexResponse = esConfig.getElastcsearchClient().index(indexRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (indexResponse.getResult() != DocWriteResponse.Result.CREATED)
			throw new IllegalStateException("Request Token saving failed");
		else
			log.info("Request Token saved successfully!!");
	}
	
	public HashMap<String, Object> getTwitterRequestToken(String value){
		SearchRequest searchRequest = new SearchRequest(ESIndices.TWITTER_REQUEST_TOKEN_INDEX);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.termQuery("value", value)).size(1);
		searchRequest.source(sourceBuilder);
		
		try {
			RestHighLevelClient client = esConfig.getElastcsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			if(searchResponse.getHits().getTotalHits().value > 0L)
			{
				Map<String, Object> src = null;
				for (SearchHit hit: searchResponse.getHits()) {
					src = hit.getSourceAsMap();
					return (HashMap<String, Object>) src;
				}
			}
		} catch (Exception e) {
			log.error("Error getting request token");
		}
		return null;
	}
	
	public void persistTwitterAccessToken(String userId, String value, String secret) {
		TwitterOauthTokenPersist token = new TwitterOauthTokenPersist(userId, value, secret, 1L);
		IndexRequest indexRequest = null;
		try {
			indexRequest = new IndexRequest(ESIndices.TWITTER_OAUTH_TOKEN_INDEX).id(userId)
					.source(new ObjectMapper().writeValueAsString(token),XContentType.JSON);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		indexRequest.opType(DocWriteRequest.OpType.INDEX); 
		IndexResponse indexResponse = null;
		try {
			indexResponse = esConfig.getElastcsearchClient().index(indexRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (indexResponse.getResult() != DocWriteResponse.Result.CREATED)
			throw new IllegalStateException("Twitter Oauth Token saving failed");
		else
			log.info("Twitter Oauth Token saved successfully!!");
		
	}
	
	public TwitterOauthTokenPersist getTwitterAccessToken(String userId) {
		SearchRequest searchRequest = new SearchRequest(ESIndices.TWITTER_OAUTH_TOKEN_INDEX);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.termQuery("userId", userId)).size(1);
		searchRequest.source(sourceBuilder);
		
		try {
			RestHighLevelClient client = esConfig.getElastcsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			if(searchResponse.getHits().getTotalHits().value > 0L)
			{
				for (SearchHit hit: searchResponse.getHits()) {
					TwitterOauthTokenPersist token = new ObjectMapper().readValue(hit.getSourceAsString(), TwitterOauthTokenPersist.class);
					return token;
				}
			}
		} catch (Exception e) {
			log.error("Error getting twitter access token");
		}
		return new TwitterOauthTokenPersist();
	}
	
	public long checkTwitterOauthToken(String userId) {
		
		SearchRequest searchRequest = new SearchRequest(ESIndices.TWITTER_OAUTH_TOKEN_INDEX);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.termQuery("userId", userId));
		searchRequest.source(sourceBuilder);
		try {
			RestHighLevelClient client = esConfig.getElastcsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			return searchResponse.getHits().getTotalHits().value;
		} catch (Exception e) {
			log.error("Error getting twitter access token");
		}
		return 0;
	}
	
	// gets a user document from ES given username
	public User getUserDocument(String username) {
		SearchRequest searchRequest = new SearchRequest(ESIndices.USER_INDEX);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.termQuery("username", username)).size(1);
		searchRequest.source(sourceBuilder);
		
		try {
			RestHighLevelClient client = esConfig.getElastcsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			if(searchResponse.getHits().getTotalHits().value > 0L)
			{
				Map<String, Object> src = null;
				for (SearchHit hit: searchResponse.getHits()) {
					src = hit.getSourceAsMap();
					User user = UserUtils.userFromESDocument(src);
					return user;
				}
			}
			else
				return null;
		} catch (Exception e) {
			log.error("Error getting document for user: " + username , e);
		}
		return null;
	}

	// creates a User document in ES with the given User
	public String createUserDocument(User user) {
		Map<String, Object> jsonMap = UserUtils.jsonPropertiesFromUser(user);
		IndexRequest indexRequest = new IndexRequest(ESIndices.USER_INDEX).id(user.getId()).source(jsonMap);
		indexRequest.opType(DocWriteRequest.OpType.INDEX);
		try {
			IndexResponse indexResponse = esConfig.getElastcsearchClient().index(
					indexRequest, RequestOptions.DEFAULT);
			if (indexResponse.getResult() != DocWriteResponse.Result.CREATED)
				throw new IllegalStateException("User document was not created for id: " + user.getId());
			else
			{
				log.info("User document created successfully for id: " + user.getId());
			}
		} catch (IOException e) {
			
			log.error("Unable to create user document in ES", e);
		}
		return null;
	}
	
	public void saveUserDetails(UserDetails ud) throws Exception {
		System.out.println(ud.getCity() + " "  + ud.getState() + " " + ud.getCountry() + " " + ud.getBaseCurrency() + " " + ud.getUserId());
		Map<String, Object> loc = new HashMap<String, Object>();
		loc.put("city", ud.getCity());
		loc.put("state", ud.getState());
		loc.put("country", ud.getCountry());
		
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index(ESIndices.USER_INDEX);
		updateRequest.id(ud.getUserId());
		updateRequest.doc( XContentFactory.jsonBuilder()
				.startObject()
				.field("location", loc)
				.field("base_currency", ud.getBaseCurrency())
				.field("filled_details", true)
				.endObject());
		try {
			UpdateResponse update = esConfig.getElastcsearchClient().update(updateRequest, RequestOptions.DEFAULT);
			log.info("User details updated successfully");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	private String createTemporaryUserDocument(User user, String secret) {
//		Map<String, Object> jsonMap = UserUtils.jsonPropertiesFromUser(user);
//		jsonMap.put("secret", secret);
//		IndexRequest indexRequest = null;
//		indexRequest = new IndexRequest(USER_INDEX).id(user.getId()).source(jsonMap);
//		indexRequest.opType(DocWriteRequest.OpType.INDEX);
//		 
//		try {
//			IndexResponse indexResponse = esConfig.getElastcsearchClient().index(
//					indexRequest, RequestOptions.DEFAULT);
//			if (indexResponse.getResult() != DocWriteResponse.Result.CREATED)
//				throw new IllegalStateException("Temp User document was not created for user: " + user.getUsername());
//			else
//			{
//				log.info("Temp User document created successfully for user: " + user.getUsername());
//				return indexResponse.getId();
//			}
//		} catch (IOException e) {
//			
//			log.error("Unable to create user document in ES", e);
//		}
//		return null;
//	}
	
	public void updateProfile() {
		
	}
	
//	public void updateProfilePicture(String userId, String pictureId) {
//		try {
//			IndexRequest indexRequest = new IndexRequest(USER_INDEX, "_doc")
//			        .source(new UserTimelineIndexAdapter().indexJson(uti));
//			esConfig.getElastcsearchClient().index(indexRequest, RequestOptions.DEFAULT);
//		} catch (Exception e) {
//			log.error("Error inserting user timeline event in index: " + uti.getActorId(), e);
//		} 
//	}

	protected void sendMessageAsynch(UserTimelineItem uti) {
		te.execute(new SenderRunnable<UserActionsAsynchMessageSender, UserTimelineItem>(uaam, uti));
	}

	protected void sendMessageAsynch(UserEmailVerifyToken uevt) {
		te.execute(new SenderRunnable<UserActionsAsynchMessageSender, UserEmailVerifyToken>(uaam, uevt));
	}
	
	protected void sendMessageAsynch(GiveUpLike gul) {
		te.execute(new SenderRunnable<UserActionsAsynchMessageSender, GiveUpLike>(uaam, gul));
	}
	
}
