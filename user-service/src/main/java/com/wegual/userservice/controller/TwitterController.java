package com.wegual.userservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wegual.userservice.twitter.TwitterInstanceCreator;
import com.wegual.userservice.twitter.TwitterService;

import app.wegual.common.model.TwitterOauthTokenPersist;


@RestController
public class TwitterController {

	@Autowired
	private TwitterService twitterService;

	@Autowired
	private TwitterInstanceCreator ttc;

	// Step-1
	@GetMapping("/twitter/oauth_verifier")
	void getOauthVerifier() throws Exception{
		ttc.getOauthVerifier();
		return;
	}

	// Step-2
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping(value = "/auth/twitter")
	public String twitterAuthUrl() throws Exception {
		return ttc.getAuthorizeUrl();
	}

	// Step-3
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping(value = "/twitter/oauth/save")
	public void saveTwitterOauthToken(@RequestParam("userId") String userId, @RequestParam("oauthVerifier") String oauthVerifier, 
			@RequestParam("requestToken") String requestToken) throws Exception {
		ttc.generateAccessToken(userId, oauthVerifier, requestToken);
		return;
	}

	//@PreAuthorize("#oauth2.hasScope('user-service')")
	@PostMapping(value = "/twitter/tweet")
	void postTweet(@RequestParam("tweetText") String tweetText, @RequestParam("userId") String userId) throws Exception{
		twitterService.postTweet(userId, tweetText);
		return;
	}

	//@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping(value = "/twitter/tweet")
	ResponseEntity<String> getAllTweets(@RequestParam("userId") String userId) throws Exception{
		return( new ResponseEntity<String>(twitterService.getAllTweet(userId), HttpStatus.OK));
	}
	
	@PostMapping(value = "/tweets/after")
	String fetchTweetsAfterId(@RequestBody TwitterOauthTokenPersist accessToken){
		try {
			return twitterService.fetchTweetsAfterId(accessToken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}