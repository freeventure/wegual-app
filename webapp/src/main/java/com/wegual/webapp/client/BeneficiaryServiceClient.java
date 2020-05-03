package com.wegual.webapp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import app.wegual.common.model.Beneficiary;
import app.wegual.common.rest.model.BeneficiarySnapshot;

@FeignClient("beneficiary-service")
public interface BeneficiaryServiceClient {

	 @GetMapping("/beneficiary/{benid}")
	    Beneficiary getBeneficiary(@RequestHeader(value = "Authorization", required = true) String token,
	    		 @PathVariable Long benid);
	 @GetMapping("/beneficiary/snapshot/{benid}")
	    BeneficiarySnapshot getBeneficiarySnapshot(@RequestHeader(value = "Authorization", required = true) String token,
	    		 @PathVariable Long benid);
}