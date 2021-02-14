package app.wegual.common.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwitterOauthTokenPersist implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String value;
	private String secret;
	
	@JsonProperty("recent_fetched_tweet_id")
	private Long recentFetchedTweetId;
	
	public TwitterOauthTokenPersist(String userId, String value, String secret, Long timestamp) {
		this.userId = userId;
		this.value = value;
		this.secret = secret;
		this.recentFetchedTweetId = timestamp;
	}

	public TwitterOauthTokenPersist() {	}

}
