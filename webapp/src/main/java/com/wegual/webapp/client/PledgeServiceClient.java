package com.wegual.webapp.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import app.wegual.common.model.Pledge;
import app.wegual.common.model.PledgeAnalyticsForUser;
import app.wegual.common.model.RegisterPledge;

@FeignClient("pledge-service")
public interface PledgeServiceClient {

	@GetMapping("/pledge/{pledgeid}")
	Pledge getPledge(@RequestHeader(value = "Authorization", required = true) String token,
			@PathVariable String pledgeid);
	
	@PostMapping(value = "/pledge/save")
	void savePledge(@RequestHeader(value = "Authorization", required = true) String token,
   		 @RequestBody RegisterPledge ra) ;
	
	@GetMapping("/users/pledges/count/{userid}")
	PledgeAnalyticsForUser getPledgeCounts(@RequestHeader(value = "Authorization", required = true) String token,
			@PathVariable String userid);
	
	@GetMapping("/users/pledges/{userid}")
	List<Map<String, Object>> getAllPledgesForUser(@RequestHeader(value = "Authorization", required = true) String token, 
			@PathVariable String userid);
	
	@GetMapping("/pledge/count")
	Long getAllPledgeCount(@RequestHeader(value = "Authorization", required = true) String token);
	
}

