package com.wegual.userservice.twitter;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryRequestBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wegual.userservice.ElasticSearchConfig;
import com.wegual.userservice.UserUtils;
import com.wegual.userservice.es.actions.UserFeedIndexAdapter;
import com.wegual.userservice.service.TwitterFeedItemBuilder;
import com.wegual.userservice.service.UserActionsService;

import app.wegual.common.model.GenericItem;
import app.wegual.common.model.TwitterFeedItem;
import app.wegual.common.model.TwitterOauthTokenPersist;
import app.wegual.common.util.ESIndices;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class TwitterService {

	@Autowired
	private TwitterInstanceCreator ttc;

	@Autowired
	private UserActionsService uas;

	@Autowired
	private ElasticSearchConfig esConfig;

	private static String apiKey = "T7iqTGbWLHme8eW1DnzwoZxiD";
	private static String apiSecret = "yPWVzMTtCePrGurSUxTEaFfSAgNlTUG4tOMqovkdC0isXWkcrZ";
	private String [] reqFields = {"created_at", "id_str", "text"};

	public String postTweet(String userId, String tweetContent) throws Exception {
		String url = "https://api.twitter.com/1.1/statuses/update.json?status=" + tweetContent;
		//String authHeader = ttc.generateQueryAuthHeader(userId, url, "POST");
		String requestType = "POST";
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
		+ oauth_timestamp + "&oauth_version=1.0" + "&status=" + tweetContent;	

		System.out.println("parameter_string=" + parameter_string);

		String signature_base_string = requestType + "&" + ttc.encode(url) + "&" + URLEncoder.encode(parameter_string, "UTF-8");
		System.out.println("signature_base_string=" + signature_base_string);
		String oauth_signature = "";
		try {
			oauth_signature = ttc.computeSignature(signature_base_string, apiSecret + "&" + accessToken.getSecret());
			System.out.println("oauth_signature=" + URLEncoder.encode(oauth_signature, "UTF-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String authorization_header_string = "OAuth oauth_consumer_key=\"" + oauth_consumer_key + ",oauth_token=\"" + accessToken.getValue() +  "\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"" + 
				oauth_timestamp + "\",oauth_nonce=\"" + oauth_nonce + "\",oauth_version=\"1.0\",oauth_signature=\"" 
				+ URLEncoder.encode(oauth_signature, "UTF-8") + "\"";
		System.out.println(authorization_header_string);
		OkHttpClient client = new OkHttpClient().newBuilder()
				.build();
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = RequestBody.create(mediaType, "");
		Request request = new Request.Builder()
				.url(url)
				.method("POST", body)
				.addHeader("Authorization", authorization_header_string)
				//.addHeader("Cookie", "personalization_id=\"v1_ko6YMldxtW7qpgEJNAJ3Hw==\"; guest_id=v1%3A160238938386003467")
				.build();
		Response response = client.newCall(request).execute();
		System.out.println(response.body().string());
		return response.body().string();
	}

	public String getAllTweet(String userId) throws Exception {

		TwitterOauthTokenPersist accessToken = uas.getTwitterAccessToken(userId);

		// generate authorization header
		String get_or_post = "GET";
		String oauth_signature_method = "HMAC-SHA1";

		String uuid_string = UUID.randomUUID().toString();
		uuid_string = uuid_string.replaceAll("-", "");
		String oauth_nonce = uuid_string; // any relatively random alphanumeric string will work here

		// get the timestamp
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String oauth_timestamp = (new Long(timestamp.getTime()/1000)).toString();

		// the parameter string must be in alphabetical order
		// this time, I add 3 extra params to the request, "lang", "result_type" and "q".
		String parameter_string = "oauth_consumer_key=" + apiKey + "&oauth_nonce=" + oauth_nonce + "&oauth_signature_method=" + oauth_signature_method + 
				"&oauth_timestamp=" + oauth_timestamp + "&oauth_token=" + ttc.encode(accessToken.getValue()) + "&oauth_version=1.0";	
		String twitter_endpoint = "https://api.twitter.com/1.1/statuses/user_timeline.json";
		String signature_base_string = get_or_post + "&"+ ttc.encode(twitter_endpoint) + "&" + ttc.encode(parameter_string);

		// this time the base string is signed using twitter_consumer_secret + "&" + encode(oauth_token_secret) instead of just twitter_consumer_secret + "&"
		String oauth_signature = "";
		try {
			oauth_signature = ttc.computeSignature(signature_base_string, apiSecret + "&" + ttc.encode(accessToken.getSecret()));  // note the & at the end. Normally the user access_token would go here, but we don't know it yet for request_token
		} catch (Exception e) {
			e.printStackTrace();
		}

		String authorization_header_string = "OAuth oauth_consumer_key=\"" + apiKey + "\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"" + oauth_timestamp + 
				"\",oauth_nonce=\"" + oauth_nonce + "\",oauth_version=\"1.0\",oauth_signature=\"" + ttc.encode(oauth_signature) + "\",oauth_token=\"" + ttc.encode(accessToken.getValue()) + "\"";

		OkHttpClient client = new OkHttpClient().newBuilder()
				.build();
		Request request = new Request.Builder()
				.url(twitter_endpoint)
				.method("GET", null)
				.addHeader("Authorization", authorization_header_string)
				//.addHeader("Cookie", "personalization_id=\"v1_ko6YMldxtW7qpgEJNAJ3Hw==\"; guest_id=v1%3A160238938386003467")
				.build();
		Response response = client.newCall(request).execute();
		String tweets = response.body().string();
		System.out.println("========>>>>>>>>> " + tweets);
		return tweets;
	}

	public String fetchTweetsAfterId(TwitterOauthTokenPersist accessToken) throws Exception {
		// generate authorization header
		String get_or_post = "GET";
		String oauth_signature_method = "HMAC-SHA1";

		String uuid_string = UUID.randomUUID().toString();
		uuid_string = uuid_string.replaceAll("-", "");
		String oauth_nonce = uuid_string; // any relatively random alphanumeric string will work here

		// get the timestamp
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String oauth_timestamp = (new Long(timestamp.getTime()/1000)).toString();

		// the parameter string must be in alphabetical order
		// this time, I add 3 extra params to the request, "lang", "result_type" and "q".
		String parameter_string = "oauth_consumer_key=" + apiKey + "&oauth_nonce=" + oauth_nonce + "&oauth_signature_method=" + oauth_signature_method + 
				"&oauth_timestamp=" + oauth_timestamp + "&oauth_token=" + ttc.encode(accessToken.getValue()) + "&oauth_version=1.0";
		System.out.println(parameter_string);
		String twitter_endpoint = "https://api.twitter.com/1.1/statuses/user_timeline.json?count=200&since_id" + accessToken.getRecentFetchedTweetId();
		System.out.println(twitter_endpoint);
		String signature_base_string = get_or_post + "&"+ ttc.encode(twitter_endpoint) + "&" + ttc.encode(parameter_string);
		//twitter_endpoint += "?count=200&since_id=" + accessToken.getRecentFetchedTweetId();
		System.out.println(signature_base_string);
		// this time the base string is signed using twitter_consumer_secret + "&" + encode(oauth_token_secret) instead of just twitter_consumer_secret + "&"
		String oauth_signature = "";
		try {
			oauth_signature = ttc.computeSignature(signature_base_string, apiSecret + "&" + ttc.encode(accessToken.getSecret()));  // note the & at the end. Normally the user access_token would go here, but we don't know it yet for request_token
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(oauth_signature);
		String authorization_header_string = "OAuth oauth_consumer_key=\"" + apiKey + "\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"" + oauth_timestamp + 
				"\",oauth_nonce=\"" + oauth_nonce + "\",oauth_version=\"1.0\",oauth_signature=\"" + ttc.encode(oauth_signature) + "\",oauth_token=\"" + ttc.encode(accessToken.getValue()) + "\"";

		OkHttpClient client = new OkHttpClient().newBuilder()
				.build();
		Request request = new Request.Builder()
				.url(twitter_endpoint)
				.method("GET", null)
				.addHeader("Authorization", authorization_header_string)
				//.addHeader("Cookie", "personalization_id=\"v1_ko6YMldxtW7qpgEJNAJ3Hw==\"; guest_id=v1%3A160238938386003467")
				.build();
		Response response = client.newCall(request).execute();
		String tweets = response.body().string();
		System.out.println("========>>>>>>>>> " + tweets);
		return tweets;
	}

	public String fetchTweetsUptoId(TwitterOauthTokenPersist accessToken, Long maxId) {
		// generate authorization header
		String get_or_post = "GET";
		String oauth_signature_method = "HMAC-SHA1";

		String uuid_string = UUID.randomUUID().toString();
		uuid_string = uuid_string.replaceAll("-", "");
		String oauth_nonce = uuid_string; // any relatively random alphanumeric string will work here

		// get the timestamp
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String oauth_timestamp = (new Long(timestamp.getTime()/1000)).toString();

		// the parameter string must be in alphabetical order
		// this time, I add 3 extra params to the request, "lang", "result_type" and "q".
		String parameter_string = "oauth_consumer_key=" + apiKey + "&oauth_nonce=" + oauth_nonce + "&oauth_signature_method=" + oauth_signature_method + 
				"&oauth_timestamp=" + oauth_timestamp + "&oauth_token=" + ttc.encode(accessToken.getValue()) + "&oauth_version=1.0";	
		String twitter_endpoint = "https://api.twitter.com/1.1/statuses/user_timeline.json?count=200&max_id=" + maxId;
		String signature_base_string = get_or_post + "&"+ ttc.encode(twitter_endpoint) + "&" + ttc.encode(parameter_string);

		// this time the base string is signed using twitter_consumer_secret + "&" + encode(oauth_token_secret) instead of just twitter_consumer_secret + "&"
		String oauth_signature = "";
		try {
			oauth_signature = ttc.computeSignature(signature_base_string, apiSecret + "&" + ttc.encode(accessToken.getSecret()));  // note the & at the end. Normally the user access_token would go here, but we don't know it yet for request_token
		} catch (Exception e) {
			e.printStackTrace();
		}

		String authorization_header_string = "OAuth oauth_consumer_key=\"" + apiKey + "\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"" + oauth_timestamp + 
				"\",oauth_nonce=\"" + oauth_nonce + "\",oauth_version=\"1.0\",oauth_signature=\"" + ttc.encode(oauth_signature) + "\",oauth_token=\"" + ttc.encode(accessToken.getValue()) + "\"";

		OkHttpClient client = new OkHttpClient().newBuilder()
				.build();
		Request request = new Request.Builder()
				.url(twitter_endpoint)
				.method("GET", null)
				.addHeader("Authorization", authorization_header_string)
				//.addHeader("Cookie", "personalization_id=\"v1_ko6YMldxtW7qpgEJNAJ3Hw==\"; guest_id=v1%3A160238938386003467")
				.build();
		Response response;
		String tweets = null;
		try {
			response = client.newCall(request).execute();
			tweets = response.body().string();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("========>>>>>>>>> " + tweets);
		return tweets;
	}

	public Map<String, Object> stringTweetToMap(String tweetStr) {
		Map<String, Object> tweet = new HashMap<String, Object>();
		String[] keyValuePairs = tweetStr.split(",");
		for(String keyValues : keyValuePairs) {
			String[] entry = keyValues.split(":");
			if(Arrays.asList(reqFields).contains(entry[0])) {
				if(entry[0].equals(reqFields[0])) {
					SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
					Date date = null;
					try {
						date = sdf.parse(entry[1].substring(1, entry[1].length()-1));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					tweet.put(reqFields[0], date.getTime());
				}
				if(entry[0].equals(reqFields[1])) {
					tweet.put(reqFields[1], entry[1].substring(1, entry[1].length()-1));
				}
				if(entry[0].equals(reqFields[2])) {
					tweet.put(reqFields[2], entry[1].substring(1, entry[1].length()-1));
				}
			}
			else {
				continue;
			}
		}
		return tweet;
	}

	public void updateLastFetchId(String userId, Long updatedId) {
		UpdateByQueryRequestBuilder updateByQuery =
				new UpdateByQueryRequestBuilder((ElasticsearchClient) esConfig.getElastcsearchClient(), UpdateByQueryAction.INSTANCE);
		updateByQuery.source(ESIndices.TWITTER_OAUTH_TOKEN_INDEX)
		.filter(QueryBuilders.termQuery("userId", userId))
		.maxDocs(1000)
		.script(new Script(ScriptType.INLINE,
				"painless",
				"ctx._source.recent_fetched_tweet_id = 'absolutely'",
				Collections.emptyMap()));
		BulkByScrollResponse response = updateByQuery.get();
	}

	public void ingestTweetsFromTwitter(String userId) {
		TwitterOauthTokenPersist accessToken = uas.getTwitterAccessToken(userId);
		String response = null;
		Long maxId, lastFetchId; 
		lastFetchId = maxId = accessToken.getRecentFetchedTweetId();
		Long minId = Long.MAX_VALUE;
		try {
			response = fetchTweetsAfterId(accessToken);
			response = response.substring(1, response.length()-1);
			String [] tweetsString = response.split(",");
			for(String tweetStr : tweetsString) {
				Map<String, Object> tweet = stringTweetToMap(tweetStr);
				if(Long.parseLong((String) tweet.get("id_str"))<=lastFetchId) {
					continue;
				}
				TwitterFeedItem tfi = new TwitterFeedItemBuilder().feed(tweet, userId).build();
				IndexRequest indexRequest = new IndexRequest(ESIndices.USER_FEED_INDEX).source(new UserFeedIndexAdapter().indexJson(tfi));
				esConfig.getElastcsearchClient().index(indexRequest, RequestOptions.DEFAULT);
				maxId = Long.max(maxId, Long.parseLong((String) tweet.get("id_str")));
				minId = Long.min(minId, Long.parseLong((String) tweet.get("id_str")));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		do {
			try {
				response = fetchTweetsUptoId(accessToken, minId);
				response = response.substring(1, response.length()-1);
				System.out.println(response);
				String [] tweetsString = response.split(",");
				for(String tweetStr : tweetsString) {
					System.out.println(tweetStr);
					Map<String, Object> tweet = stringTweetToMap(tweetStr);
					TwitterFeedItem tfi = new TwitterFeedItemBuilder().feed(tweet, userId).build();
					IndexRequest indexRequest = new IndexRequest(ESIndices.USER_FEED_INDEX).source(new UserFeedIndexAdapter().indexJson(tfi));
					esConfig.getElastcsearchClient().index(indexRequest, RequestOptions.DEFAULT);
					System.out.println((String)tweet.get("id_str"));
					maxId = Long.max(maxId, Long.parseLong((String) tweet.get("id_str")));
					minId = Long.min(minId, Long.parseLong((String) tweet.get("id_str")));
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		} while(response.length()!=0);
		updateLastFetchId(userId, maxId);
		return;
	}

}
