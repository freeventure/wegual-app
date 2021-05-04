package com.wegual.giveupservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.wegual.giveupservice.service.GiveUpFollowService;
import com.wegual.giveupservice.service.GiveUpService;

import app.wegual.common.model.GenericItem;
import app.wegual.common.model.GiveUp;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class GiveUpController {
	
	@Autowired
	private GiveUpService gus;
	
	@Autowired
	private GiveUpFollowService gufs;
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/giveup/all")
	ResponseEntity<List<GiveUp>> getAllGiveUp() {
		try {
			List<GiveUp> giveups = gus.getAllGiveup();
			return new ResponseEntity<List<GiveUp>>(giveups, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<List<GiveUp>>(new ArrayList<GiveUp>(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/giveup/suggest/like/{userId}")
	public List<GiveUp> suggestGiveUpToLike(@PathVariable("userId") String userId){
		log.info("Inside GiveUp Controller to suggest giveups");
		try {
			return gus.suggestGiveUpToLike(userId);
		} catch (Exception e) {
			log.info("Error suggesting giveups to like for user Id" + userId,e);
		}
		return null;
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/giveup/suggest/follow/{userId}")
	public List<GiveUp> suggestGiveUpToFollow(@PathVariable("userId") String userId){
		log.info("Inside GiveUp Controller to suggest giveups to follow");
		try {
			return gufs.suggestGiveUpToFollow(userId);
		} catch (Exception e) {
			log.info("Error suggesting giveups to like for user Id" + userId, e);
		}
		return null;
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/giveup/likedby/{userId}")
	public List<GenericItem<String>> allGiveUpLikedByUser(@PathVariable("userId") String userId){
		log.info("Inside GiveUp Controller to find all giveups like by userId " + userId);
		try {
			return gus.allGiveUpLikedByUser(userId);
		} catch (Exception e) {
			log.info("Error finding giveups liked by user Id" + userId);
		}
		return null;
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/giveup/followedby/{userId}")
	public List<GenericItem<String>> allGiveUpFollowedByUser(@PathVariable("userId") String userId){
		log.info("Inside GiveUp Controller to find all giveups followed by userId " + userId);
		try {
			return gufs.allGiveUpFollowedByUser(userId);
		} catch (Exception e) {
			log.info("Error finding giveups followed by user Id" + userId, e);
		}
		return null;
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/giveup/pledge/{userId}")
	public List<GenericItem<String>> getAllGiveupUserPledgedFor(@PathVariable("userId") String userId){
		log.info("Inside GiveUp Controller to find all giveups user pledged for with userId " + userId);
		try {
			return gus.getAllGiveupUserPledgedFor(userId);
		} catch (Exception e) {
			log.info("Error finding giveups user pledged for with user Id" + userId);
		}
		return null;
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/giveup/suggestByName/{name}")
	List<GenericItem<String>> suggestGiveupByName(@PathVariable String name) {
		try {
			return gus.getSuggestionSearch(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<GenericItem<String>>();
	}

	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/giveup/count")
	ResponseEntity<Long> getAllGiveupCount(){
		try {
			long giveupCount = gus.getGiveupCount();
			return new ResponseEntity<Long>(giveupCount, HttpStatus.OK);
		}
		catch(Exception e) {
			log.error("Eroor getting giveup count");
			return new ResponseEntity<Long>(new Long(0), HttpStatus.BAD_REQUEST);
		}
	}
	
}
