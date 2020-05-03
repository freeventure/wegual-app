package com.wegual.beneficiaryservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.wegual.beneficiaryservice.service.BeneficiaryService;

import app.wegual.common.model.Beneficiary;
import app.wegual.common.rest.model.BeneficiarySnapshot;
@RestController
public class BeneficiaryController {
	
	@Autowired
	BeneficiaryService bs;
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/beneficiary/{benid}")
	ResponseEntity<Beneficiary> getBeneficiary(@PathVariable("benid") Long benId) {
		try {
			Beneficiary ben = bs.getBeneficiary(benId);
			return new ResponseEntity<Beneficiary>(ben, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Beneficiary>(new Beneficiary(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/beneficiary/snapshot/{benid}")
	ResponseEntity<BeneficiarySnapshot> getBeneficiarySnapshot(@PathVariable("benid") Long benId) {
		try {
			BeneficiarySnapshot benSnapshot = bs.getBeneficiarySnapshot(benId);
			return new ResponseEntity<BeneficiarySnapshot>(benSnapshot, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<BeneficiarySnapshot>(new BeneficiarySnapshot(), HttpStatus.BAD_REQUEST);
		}
		
	}

}