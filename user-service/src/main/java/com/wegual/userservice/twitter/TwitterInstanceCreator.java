package com.wegual.userservice.twitter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.social.twitter.connect.TwitterServiceProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wegual.userservice.service.UserActionsService;

import app.wegual.common.model.TwitterOauthTokenPersist;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class TwitterInstanceCreator {

	@Autowired
	private UserActionsService uas;

	private final RestTemplate restTemplate;
	
	private static String apiKey = "T7iqTGbWLHme8eW1DnzwoZxiD";
	private static String apiSecret = "yPWVzMTtCePrGurSUxTEaFfSAgNlTUG4tOMqovkdC0isXWkcrZ";

	public TwitterInstanceCreator(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public String computeSignature(String baseString, String keyString) throws Exception {

		SecretKey secretKey = null;
		byte[] keyBytes = keyString.getBytes();
		secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(secretKey);
		byte[] text = baseString.getBytes();
		return new String(Base64.encode(mac.doFinal(text))).trim();
	}

	public String encode(String value){
		String encoded = null;
		try {
			encoded = URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException ignore) {
		}
		StringBuilder buf = new StringBuilder(encoded.length());
		char focus;
		for (int i = 0; i < encoded.length(); i++) {
			focus = encoded.charAt(i);
			if (focus == '*') {
				buf.append("%2A");
			} else if (focus == '+') {
				buf.append("%20");
			} else if (focus == '%' && (i + 1) < encoded.length()
					&& encoded.charAt(i + 1) == '7' && encoded.charAt(i + 2) == 'E') {
				buf.append('~');
				i += 2;
			} else {
				buf.append(focus);
			}
		}
		return buf.toString();
	}

	public String getRequestHeaders(String url, String requestType) throws Exception {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String oauth_signature_method = "HMAC-SHA1";
		String oauth_consumer_key = apiKey;
		String uuid_string = UUID.randomUUID().toString();
		uuid_string = uuid_string.replaceAll("-", "");
		String oauth_nonce = uuid_string;
		String oauth_timestamp = (new Long(timestamp.getTime()/1000)).toString();
		String parameter_string = "oauth_consumer_key=" + oauth_consumer_key + "&oauth_nonce=" + oauth_nonce + "&oauth_signature_method=" 
				+ oauth_signature_method + "&oauth_timestamp=" + oauth_timestamp + "&oauth_version=1.0";	

		System.out.println("parameter_string=" + parameter_string);

		String signature_base_string = requestType + "&" + encode("https://api.twitter.com/oauth/request_token") + "&" + URLEncoder.encode(parameter_string, "UTF-8");
		System.out.println("signature_base_string=" + signature_base_string);
		String oauth_signature = "";
		try {
			oauth_signature = computeSignature(signature_base_string, apiSecret+"&");
			System.out.println("oauth_signature=" + URLEncoder.encode(oauth_signature, "UTF-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String authorization_header_string = "OAuth oauth_consumer_key=\"" + oauth_consumer_key + "\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"" + 
				oauth_timestamp + "\",oauth_nonce=\"" + oauth_nonce + "\",oauth_version=\"1.0\",oauth_signature=\"" 
				+ URLEncoder.encode(oauth_signature, "UTF-8") + "\"";

		return authorization_header_string;
	}

	public String generateQueryAuthHeader(String userId, String url, String requestType) throws Exception {
		TwitterOauthTokenPersist accessToken = uas.getTwitterAccessToken(userId);
		System.out.println(accessToken.getValue());
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String oauth_signature_method = "HMAC-SHA1";
		String oauth_consumer_key = apiKey;
		String uuid_string = UUID.randomUUID().toString();
		uuid_string = uuid_string.replaceAll("-", "");
		String oauth_nonce = uuid_string;
		String oauth_timestamp = (new Long(timestamp.getTime()/1000)).toString();
		String parameter_string = "oauth_consumer_key=" + oauth_consumer_key + "&oauth_token" + accessToken.getValue() 
		+ "&oauth_nonce=" + oauth_nonce + "&oauth_signature_method="  + oauth_signature_method + "&oauth_timestamp=" 
		+ oauth_timestamp + "&oauth_version=1.0";	

		System.out.println("parameter_string=" + parameter_string);

		String signature_base_string = requestType + "&" + encode(url) + "&" + URLEncoder.encode(parameter_string, "UTF-8");
		System.out.println("signature_base_string=" + signature_base_string);
		String oauth_signature = "";
		try {
			oauth_signature = computeSignature(signature_base_string, apiSecret + "&" + accessToken.getSecret());
			System.out.println("oauth_signature=" + URLEncoder.encode(oauth_signature, "UTF-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String authorization_header_string = "OAuth oauth_consumer_key=\"" + oauth_consumer_key + ",oauth_token=\"" + accessToken.getValue() +  "\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"" + 
				oauth_timestamp + "\",oauth_nonce=\"" + oauth_nonce + "\",oauth_version=\"1.0\",oauth_signature=\"" 
				+ URLEncoder.encode(oauth_signature, "UTF-8") + "\"";
		System.out.println(authorization_header_string);
		return authorization_header_string;
	}

	// Step-1 :- Initiating Account Permission Request
	public String getOauthToken() throws Exception {		
		String authorization_header_string = getRequestHeaders("https://api.twitter.com/oauth/request_token", "POST");

		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\n    \"oauth_consumer_key\" : \"" + apiKey + "\"\n}");
		Request request = new Request.Builder()
				.url("https://api.twitter.com/oauth/request_token")
				.method("POST", body)
				.addHeader("Authorization", authorization_header_string)
				.addHeader("Content-Type", "application/json")
				.build();
		Response response = client.newCall(request).execute();
		String responseBody = response.body().string();
		System.out.println(responseBody);
		String oauth_token = responseBody.substring(responseBody.indexOf("oauth_token=") + 12, responseBody.indexOf("&oauth_token_secret="));
		String oauth_token_secret = responseBody.substring(responseBody.indexOf("oauth_token_secret=") + 19, responseBody.length());
		uas.saveTwitterRequestToken(oauth_token, oauth_token_secret);
		System.out.println("OAuth Token persisted");
		return (oauth_token);
	}

	//Step-2 : Generating Authorization URL where user is redirected
	public String getAuthorizeUrl() throws Exception {
		String token = getOauthToken();
		String url = "https://api.twitter.com/oauth/authorize?oauth_token=" + token;
		return url;
	}

	// Step-3:- On successful authorization, fetching oauth_verifier
	public void getOauthVerifier() throws Exception {
		String url = getAuthorizeUrl();
		String authorization_header_string = getRequestHeaders(url, "GET");

		OkHttpClient client = new OkHttpClient().newBuilder()
				.build();
		Request request = new Request.Builder()
				.url(url)
				.method("GET", null)
				.addHeader("Authorization", authorization_header_string)
				.build();
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
			}

			@Override
			public void onResponse(Call call, final Response response) throws IOException {
				if (!response.isSuccessful()) {
					throw new IOException("Unexpected code " + response);
				} else {
					String resp = response.body().string();
					System.out.println(resp);
				}
			}
		});
	}

	// Step-4:- Generating Access Token for making requests on user's behalf
	public void generateAccessToken(String userId, String oauthVerifier, String oauthToken) throws Exception {	
		System.out.println(userId + oauthVerifier + oauthToken);
		String url = "https://api.twitter.com/oauth/access_token?oauth_consumer_key=" + apiKey + "&oauth_token=" + oauthToken + "&oauth_verifier=" + oauthVerifier;
		//String authorization_header_string = getRequestHeaders(url, "POST");

		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = RequestBody.create(mediaType, "");
		Request request = new Request.Builder()
				.url(url)
				.method("POST", body)
				//.addHeader("Authorization", authorization_header_string)
				.build();
		Response response = client.newCall(request).execute();
		String responseBody = response.body().string();
		System.out.println(responseBody);
		String access_token = responseBody.substring(responseBody.indexOf("oauth_token=") + 12, responseBody.indexOf("&oauth_token_secret="));
		String access_token_secret = responseBody.substring(responseBody.indexOf("oauth_token_secret=") + 19, responseBody.indexOf("&user_id="));
		System.out.println(access_token + " " + access_token_secret);
		uas.persistTwitterAccessToken(userId, access_token, access_token_secret);
		System.out.println("OAuth Token persisted");
		//return (access_token);
	}

	

	
	public Twitter getTwitterInstance(String userId) {
		TwitterOauthTokenPersist accessToken = uas.getTwitterAccessToken(userId);
		return new TwitterTemplate(apiKey, apiSecret, accessToken.getValue(), accessToken.getSecret());
	}

}
