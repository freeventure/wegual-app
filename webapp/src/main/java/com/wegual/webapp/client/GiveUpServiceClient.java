package com.wegual.webapp.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import app.wegual.common.model.GenericItem;
import app.wegual.common.model.GiveUp;

@FeignClient("giveup-service")
public interface GiveUpServiceClient {
	
	@GetMapping("/giveup/suggest/{userId}")
	public List<GiveUp> suggestGiveUpToLike(@RequestHeader(value = "Authorization", required = true) String token, @PathVariable("userId") String userId);
	
	@GetMapping("/giveup/suggest/follow/{userId}")
	public List<GiveUp> suggestGiveUpToFollow(@RequestHeader(value = "Authorization", required = true) String token, @PathVariable("userId") String userId);

	@GetMapping("/giveup/likedby/{userId}")
	public List<GenericItem<String>> allGiveUpLikedByUser(@RequestHeader(value = "Authorization", required = true) String token, @PathVariable("userId") String userId);
	
	@GetMapping("/giveup/followedby/{userId}")
	public List<GenericItem<String>> allGiveUpFollowedByUser(@RequestHeader(value = "Authorization", required = true) String token, @PathVariable("userId") String userId);

	@GetMapping("/giveup/pledge/{userId}")
	public List<GenericItem<String>> getAllGiveupUserPledgedFor(@RequestHeader(value = "Authorization", required = true) String token, @PathVariable("userId") String userId);
	
	@GetMapping("/giveup/all")
	List<GiveUp> getAllGiveup(@RequestHeader(value = "Authorization", required = true) String token);
}
