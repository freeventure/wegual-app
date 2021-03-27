package com.wegual.webapp.client;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import app.wegual.common.model.TokenStatus;
import app.wegual.common.model.User;
import app.wegual.common.model.UserDetails;
import app.wegual.common.model.GenericItem;
import app.wegual.common.model.PledgeFeedItem;
import app.wegual.common.model.UserTimelineItem;
import app.wegual.common.rest.model.UserFollowees;
import app.wegual.common.rest.model.UserFollowers;

@FeignClient("user-service")
public interface UserServiceClient {

	@PostMapping(value = "/users/public/email/token/verify", consumes = "application/x-www-form-urlencoded")
	TokenStatus verifyUserToken(@RequestParam("token") String token, @RequestParam("tokenId") String tokenId);

	@PostMapping(value = "/users/public/email/token", consumes = "application/json")
	String getUserEmailVerifyToken(@RequestHeader(value = "X-aux", required = true) String secret,
			User user);

	@GetMapping("/users/profile/{username}")
	User getUser(@RequestHeader(value = "Authorization", required = true) String token,
			@PathVariable String username);

	@GetMapping("/users/followers/{userid}")
	UserFollowers getUserFollowers(@RequestHeader(value = "Authorization", required = true) String token,
			@PathVariable String userid);

	@GetMapping("/users/following/{userid}")
	UserFollowees getUserFollowing(@RequestHeader(value = "Authorization", required = true) String token,
			@PathVariable String userid);

	@GetMapping("/users/timeline/{userid}")
	List<UserTimelineItem> getUserTimeline(@RequestHeader(value = "Authorization", required = true) String token,
			@PathVariable String userid);

	@GetMapping("/users/feed/{userid}")
	List<PledgeFeedItem> getUserFeed(@RequestHeader(value = "Authorization", required = true) String token,
			@PathVariable String userid);

	@GetMapping("/users/scrollabletimeline/{userid}/{timestamp}")
	List<UserTimelineItem> getScrollableUserTimeline(@RequestHeader(value = "Authorization", required = true) String token,
			@PathVariable("userid") String userid, @PathVariable("timestamp") long timestamp);

	@PostMapping(value = "/users/save/details")
	void saveUserDetails(@RequestHeader(value = "Authorization", required = true) String token,
			@RequestBody UserDetails ud) ;

	@GetMapping(value = "/auth/twitter")
	public String twitterAuthUrl(@RequestHeader(value = "Authorization", required = true) String token);

	@GetMapping(value = "/twitter/oauth/save")
	public void saveTwitterOauthToken(@RequestHeader(value = "Authorization", required = true) String token, @RequestParam("userId") String userId,
			@RequestParam("oauthVerifier") String oauthVerifier, @RequestParam("requestToken") String requestToken);
	
	@PostMapping(value = "/twitter/tweet")
	public void postTweet(@RequestHeader(value = "Authorization", required = true) String token, @RequestParam("tweetText") String tweetText, 
			@RequestParam("userId") String userId);
	
//	@GetMapping(value = "/twitter/tweet")
//	public List<Tweet> getTweet(@RequestHeader(value = "Authorization", required = true) String token, @RequestParam("userId") String userId);
	
	@GetMapping(value = "/twitter/tweet")
	public ResponseEntity<String> getAllTweet(@RequestHeader(value = "Authorization", required = true) String token, @RequestParam("userId") String userId);
	
	@GetMapping(value = "/users/checktwitterauth")
	public Long checkTwitterAuth(@RequestHeader(value = "Authorization", required = true) String token, @RequestParam("userId") String userId);
	
	@GetMapping("/users/suggest/{name}")
	List<GenericItem<String>> suggestUserByName(@RequestHeader(value = "Authorization", required = true) String token, @PathVariable String name);
}
