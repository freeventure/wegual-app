package com.wegual.webapp.client;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import app.wegual.common.model.GenericItem;
import app.wegual.common.model.PledgeAnalyticsForUser;
import app.wegual.common.model.User;
import app.wegual.common.model.UserTimelineItem;
import app.wegual.common.rest.model.UserFollowees;
import app.wegual.common.rest.model.UserFollowers;

@FeignClient("user-service")
public interface UserServiceClient {

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

	@GetMapping("/users/pledges/count/{userid}")
	PledgeAnalyticsForUser getPledgeCounts(@RequestHeader(value = "Authorization", required = true) String token,
			@PathVariable String userid);

	@GetMapping("/users/beneficiaryFollowee/{userid}")
	List<GenericItem<Long>> getBeneficiaryFollowees(@RequestHeader(value = "Authorization", required = true) String token, 
			@PathVariable String userid);

	@GetMapping("/users/pledges/{userid}")
	List<Map<String, Object>> getAllPledgesForUser(@RequestHeader(value = "Authorization", required = true) String token, 
			@PathVariable String userid);
	
	@GetMapping("/users/pledges/giveup/{userid}")
	List<Object> getAllGiveupUserPledgedFor(@RequestHeader(value = "Authorization", required = true) String token, 
			@PathVariable String userid);
	
}
