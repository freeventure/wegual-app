package com.wegual.pledgeservice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wegual.pledgeservice.analytics.PledgeAnalyticsService;

import app.wegual.common.rest.model.BeneficiarySnapshot;

@RestController
public class PledgeAnalyticsController {

	@Autowired
	PledgeAnalyticsService pas;
	
	@PostMapping("/pledge/analytics/count")
	ResponseEntity<Long> followersCount() {
		
	    return new ResponseEntity<>(pas.pledgeCount(), HttpStatus.OK);
	}

	@PostMapping("/pledge/analytics/snapshot")
	ResponseEntity<BeneficiarySnapshot> popularBeneficiaries(@RequestBody Map<String, Long> params) {
		
		return new ResponseEntity<>(pas.countsForBeneficiary(Long.valueOf(params.get("beneficiaryId"))), HttpStatus.OK);
	}
}
