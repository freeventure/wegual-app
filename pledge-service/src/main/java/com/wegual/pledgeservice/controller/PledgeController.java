package com.wegual.pledgeservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wegual.pledgeservice.service.PledgeAnalyticService;
import com.wegual.pledgeservice.service.PledgeService;
import app.wegual.common.model.PledgeAnalyticsForUser;
import app.wegual.common.model.RegisterPledge;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PledgeController {
	@Autowired
	PledgeService ps;
	

	@Autowired
	private PledgeAnalyticService pledgeAnalytics;
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/users/pledges/count/{userid}")
	ResponseEntity<PledgeAnalyticsForUser> getPledgeCounts(@PathVariable String userid) {
		try {
			PledgeAnalyticsForUser counts = pledgeAnalytics.getCounts(userid);
			return new ResponseEntity<PledgeAnalyticsForUser>(counts, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<PledgeAnalyticsForUser>(new PledgeAnalyticsForUser(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/users/pledges/{userid}")
	ResponseEntity<List<Map<String, Object>>> getAllPledgesForUser(@PathVariable String userid) {
		try {
			List<Map<String, Object>> pledges = ps.getAllPledgeForUser(userid);
			return new ResponseEntity<List<Map<String, Object>>>(pledges, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Map<String, Object>>>(new ArrayList<Map<String, Object>>(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@PostMapping("/pledge/save")
	void savePledge(@RequestBody RegisterPledge pledge) {
		try {
		    ps.createPledge(pledge);    
		} catch (Exception e) {
			log.error("Error occured in saving pledge ",e);
		}
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/users/logins/reminders")
	ResponseEntity<List<String>> loginReminderUsers() {
		
		List<String> users = new ArrayList<String>();
		users.add("100");
		users.add("101");
		users.add("102");
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/pledge/count")
	ResponseEntity<Long> getAllPledgeCount(){
		try {
			long pledgeCount = ps.getPledgeCount();
			return new ResponseEntity<Long>(pledgeCount, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Long>(new Long(0), HttpStatus.BAD_REQUEST);
		}
	}
}
