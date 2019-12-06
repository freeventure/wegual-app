package com.wegual.giveupservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wegual.giveupservice.analytics.GiveUpAnalyticsService;

import app.wegual.common.rest.model.GiveUpFollowers;

@RestController
public class GiveUpAnalyticsController {

	@Autowired
	GiveUpAnalyticsService guas;
	
	@PostMapping("/giveup/analytics/followers")
	ResponseEntity<GiveUpFollowers> followersCount(@RequestBody Map<String, Long> params) {
		
	    return new ResponseEntity<>(guas.followersCount(params.get("giveUpId")), HttpStatus.OK);
	}

	@GetMapping("/giveup/analytics/popular")
	ResponseEntity<List<GiveUpFollowers>> popularBeneficiaries() {
		
		return new ResponseEntity<>(guas.popularGiveUps(), HttpStatus.OK);
	}
	
	@GetMapping("/giveup/analytics/count")
	ResponseEntity<Long> giveUpCount() {
		
		return new ResponseEntity<>(guas.giveUpCount(), HttpStatus.OK);
	}
	
	
}
