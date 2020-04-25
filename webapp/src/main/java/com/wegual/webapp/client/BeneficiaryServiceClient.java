package com.wegual.webapp.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import app.wegual.common.model.Beneficiary;
import app.wegual.common.model.BeneficiaryTimelineItem;

@FeignClient("beneficiary-service")
public interface BeneficiaryServiceClient {

	 @GetMapping("/beneficiary/{username}")
	    Beneficiary getBeneficiary(@RequestHeader(value = "Authorization", required = true) String token,
	    		 @PathVariable String username);
	 @GetMapping("/beneficiary/timeline/{benid}")
	    List<BeneficiaryTimelineItem> getBeneficiaryTimeline(@RequestHeader(value = "Authorization", required = true) String token,
	    		 @PathVariable String benid);
}
