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
import com.wegual.beneficiaryservice.service.BeneficiaryTimelineService;

import app.wegual.common.model.Beneficiary;
import app.wegual.common.model.BeneficiaryTimelineItem;
import app.wegual.common.model.GenericItem;
import app.wegual.common.rest.model.BeneficiarySnapshot;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BeneficiaryController {
	
	@Autowired
	private BeneficiaryService bs;
	
	@Autowired
	private BeneficiaryTimelineService bts;
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/beneficiary/all")
	ResponseEntity<List<Beneficiary>> getAllBeneficiary() {
		try {
			List<Beneficiary> bens = bs.getAllBeneficiary();
			return new ResponseEntity<List<Beneficiary>>(bens, HttpStatus.OK);
		}
		catch(Exception e) {
			log.error(""+e);
			return new ResponseEntity<List<Beneficiary>>(new ArrayList<Beneficiary>(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/beneficiary/{benid}")
	ResponseEntity<Beneficiary> getBeneficiary(@PathVariable("benid") String benId) {
		try {
			Beneficiary ben = bs.getBeneficiary(benId);
			return new ResponseEntity<Beneficiary>(ben, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Beneficiary>(new Beneficiary(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/beneficiary/snapshot/{benid}")
	ResponseEntity<BeneficiarySnapshot> getBeneficiarySnapshot(@PathVariable("benid") String benId) {
		try {
			BeneficiarySnapshot benSnapshot = bs.getBeneficiarySnapshot(benId);
			return new ResponseEntity<BeneficiarySnapshot>(benSnapshot, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<BeneficiarySnapshot>(new BeneficiarySnapshot(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/beneficiary/suggest/{userId}")
	ResponseEntity<List<Beneficiary>> suggestBeneficiaryToFollow(@PathVariable("userId") String userId) {
		try {
			List<Beneficiary> ben = bs.suggestBeneficiaryToFollow(userId);
			return new ResponseEntity<List<Beneficiary>>(ben, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error :"+e);
			return new ResponseEntity<List<Beneficiary>>(new ArrayList<Beneficiary>(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/beneficiary/followedby/{userId}")
	ResponseEntity<List<GenericItem<String>>> allBeneficiaryFollowedByUser(@PathVariable("userId") String userId) {
		try {
			List<GenericItem<String>> ben = bs.allBeneficiaryFollowedByUser(userId);
			return new ResponseEntity<List<GenericItem<String>>>(ben, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<GenericItem<String>>>(new ArrayList<GenericItem<String>>(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/beneficiary/pledges/{userid}")
	ResponseEntity<List<GenericItem<String>>> getAllBeneficiaryUserPledgedFor(@PathVariable String userid) {
		try {
			List<GenericItem<String>> giveup = (List<GenericItem<String>>) bs.getAllBeneficiaryUserPledgedFor(userid);
			return new ResponseEntity<List<GenericItem<String>>>(giveup, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<GenericItem<String>>>(new ArrayList<GenericItem<String>>(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/beneficiary/followee/{userId}")
	ResponseEntity<List<GenericItem<String>>> getBeneficiaryFollowees(@PathVariable String userId) {
		try {
			List<GenericItem<String>> giveup = (List<GenericItem<String>>) bs.getBeneficiaryFollowees(userId);
			return new ResponseEntity<List<GenericItem<String>>>(giveup, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<GenericItem<String>>>(new ArrayList<GenericItem<String>>(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/beneficiary/timeline/{benId}")
	ResponseEntity<List<BeneficiaryTimelineItem>> getTimeline(@PathVariable String benId) {
		System.out.println("Inside Beneficiary Service");
		try {
			List<BeneficiaryTimelineItem> giveup = (List<BeneficiaryTimelineItem>) bts.getTimeline(benId);
			return new ResponseEntity<List<BeneficiaryTimelineItem>>(giveup, HttpStatus.OK);
		} catch (Exception e) {
			log.info("Error fetching ben timeline", e);
			return new ResponseEntity<List<BeneficiaryTimelineItem>>(new ArrayList<BeneficiaryTimelineItem>(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/pledgedFor/{benId}")
	public Long getGiveUpCountForBeneficiary(@PathVariable String benId) {
		return bs.getGiveUpCountForBeneficiary(benId);
	}
	
	@GetMapping("/amount/pledged/base/{benId}")
	public Double getTotalAmountPledgedForBeneficiaryinBaseCurrency(@PathVariable String benId) {
		return bs.getTotalAmountPledgedForBeneficiaryinBaseCurrency(benId);
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/beneficiary/suggestByName/{name}")
	List<GenericItem<String>> suggestBeneficiaryByName(@PathVariable String name) {
		try {
			return bs.getSuggestionSearch(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<GenericItem<String>>();
	}
	
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/beneficiary/count")
	ResponseEntity<Long> getAllBeneficiaryCount(){
		log.info("Inside Beneficiary Service");
		try {
			long benCount = bs.getBeneficiaryCount();
			return new ResponseEntity<Long>(benCount, HttpStatus.OK);
		} catch (Exception e) {
			log.info("Error fetching beneficiary count", e);
			return new ResponseEntity<Long>(new Long(0), HttpStatus.BAD_REQUEST);
		}
	}

}