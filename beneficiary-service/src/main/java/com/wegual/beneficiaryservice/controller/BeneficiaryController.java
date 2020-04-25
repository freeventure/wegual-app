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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wegual.beneficiaryservice.analytics.BeneficaryAnalyticsService;
import com.wegual.beneficiaryservice.service.BeneficiaryService;
import com.wegual.beneficiaryservice.service.BeneficiaryTimelineService;
import com.wegual.beneficiaryservice.service.KeycloakUserService;

import app.wegual.common.model.Beneficiary;
import app.wegual.common.model.BeneficiaryTimelineItem;
import app.wegual.common.model.User;
import app.wegual.common.rest.model.BeneficiaryFollowers;
import app.wegual.common.service.DBFileStorageService;

@RestController
public class BeneficiaryController {
	
	@Autowired
	KeycloakUserService kus;

	@Autowired
	BeneficiaryTimelineService bts;
	
	@Autowired
	BeneficaryAnalyticsService bas;
	
	@Autowired
	BeneficiaryService bs;
	
	@Autowired
    private DBFileStorageService dbFileStorageService;
	
	@GetMapping(value = "/beneficiary/public/profile/image/{imageid}", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getBeneficiaryProfileImage(@PathVariable String imageid)  {
		
		return dbFileStorageService.getFile(imageid).getFileData();

	}
	
	@PreAuthorize("#oauth2.hasScope('beneficiary-service')")
	@GetMapping("/beneficiary/timeline/{benid}")
	ResponseEntity<List<BeneficiaryTimelineItem>> getUserTimeline(@PathVariable String benid) {
		try {
			List<BeneficiaryTimelineItem> btiList = bts.getTimeline(benid);
			return new ResponseEntity<List<BeneficiaryTimelineItem>>(btiList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<BeneficiaryTimelineItem>>(new ArrayList<BeneficiaryTimelineItem>(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/beneficiary/followers/{benid}")
	ResponseEntity<BeneficiaryFollowers> getBeneficiaryFollowers(@PathVariable String benid) {
		try {
			BeneficiaryFollowers benFollowers = bas.followersCount(benid);
			return new ResponseEntity<BeneficiaryFollowers>(benFollowers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<BeneficiaryFollowers>(new BeneficiaryFollowers(benid, 0L), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/beneficiary/profile/{username}")
	ResponseEntity<User> getUserProfile(@PathVariable("username") String username) {
		try {
			User user = kus.getUserPrfoileData(username);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<User>(new User(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/beneficiary/{username}")
	ResponseEntity<Beneficiary> getBeneficiary(@PathVariable("username") String username) {
		try {
			Beneficiary ben = bs.getBeneficiary(username);
			return new ResponseEntity<Beneficiary>(ben, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Beneficiary>(new Beneficiary(), HttpStatus.BAD_REQUEST);
		}
		
	}

}
