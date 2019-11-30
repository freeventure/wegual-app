package app.wegual.poc.es.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.wegual.poc.es.service.BeneficaryAnalyticsService;
import app.wegual.poc.rest.model.BeneficiaryFollowers;

@RestController
public class BeneficaryAnalyticsController {

	@Autowired
	BeneficaryAnalyticsService bas;
	
	@PostMapping("/beneficiary/analytics/followers")
	ResponseEntity<BeneficiaryFollowers> followersCount(@RequestBody Map<String, Long> params) {
		
	    return new ResponseEntity<>(bas.followersCount(params.get("beneficiaryId")), HttpStatus.OK);
	}

	@PostMapping("/beneficiary/analytics/popular")
	ResponseEntity<List<BeneficiaryFollowers>> popularBeneficiaries(@RequestBody Map<String, Long> params) {
		
		return new ResponseEntity<>(bas.popularBeneficaries(), HttpStatus.OK);
	}
	
}
