package com.wegual.webapp.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import app.wegual.common.model.Beneficiary;
import app.wegual.common.model.BeneficiaryTimelineItem;
import app.wegual.common.model.GenericItem;
import app.wegual.common.rest.model.BeneficiarySnapshot;

@FeignClient("beneficiary-service")
public interface BeneficiaryServiceClient {

	@GetMapping("/beneficiary/{benid}")
	Beneficiary getBeneficiary(@RequestHeader(value = "Authorization", required = true) String token,
			@PathVariable String benid);

	@GetMapping("/beneficiary/snapshot/{benid}")
	BeneficiarySnapshot getBeneficiarySnapshot(@RequestHeader(value = "Authorization", required = true) String token,
			@PathVariable String benid);

	@GetMapping("/beneficiary/suggest/{userId}")
	List<Beneficiary> suggestBeneficiaryToFollow(@RequestHeader(value = "Authorization", required = true) String token,
			@PathVariable String userId);

	@GetMapping("/beneficiary/followedby/{userId}")
	List<GenericItem<String>> allBeneficiaryFollowedByUser(@RequestHeader(value = "Authorization", required = true) String token,
			@PathVariable String userId);

	@GetMapping("/beneficiary/pledges/{userid}")
	List<GenericItem<String>> getAllBeneficiaryUserPledgedFor(@RequestHeader(value = "Authorization", required = true) String token,
			@PathVariable String userid);

	@GetMapping("/beneficiary/followee/{userId}")
	List<GenericItem<String>> getBeneficiaryFollowees(@RequestHeader(value = "Authorization", required = true) String token,
			@PathVariable String userId);

	@GetMapping("/beneficiary/all")
	List<Beneficiary> getAllBeneficiary(@RequestHeader(value = "Authorization", required = true) String token);
	
	@GetMapping("/beneficiary/timeline/{benId}")
	List<BeneficiaryTimelineItem> getTimeline(@RequestHeader(value = "Authorization", required = true) String token,
			@PathVariable String benId);
	
	@GetMapping("/beneficiary/suggestByName/{name}")
	List<GenericItem<String>> suggestBeneficiaryByName(@RequestHeader(value = "Authorization", required = true) String token, @PathVariable String name);

	@GetMapping("/beneficiary/count")
	Long getAllBeneficiaryCount(@RequestHeader(value = "Authorization", required = true) String token);
}