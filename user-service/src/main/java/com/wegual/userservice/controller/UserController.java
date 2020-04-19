package com.wegual.userservice.controller;

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

import com.wegual.userservice.analytics.UserAnalyticsService;
import com.wegual.userservice.service.KeycloakUserService;
import com.wegual.userservice.service.UserActionsService;
import com.wegual.userservice.service.UserTimelineService;

import app.wegual.common.model.User;
import app.wegual.common.model.UserTimelineItem;
import app.wegual.common.rest.model.UserFollowees;
import app.wegual.common.rest.model.UserFollowers;
import app.wegual.common.service.DBFileStorageService;


@RestController
public class UserController {
	
	@Autowired
	KeycloakUserService kus;
	
	@Autowired
	UserAnalyticsService uas;

	@Autowired
	UserActionsService uactsvc;
	
	@Autowired
	UserTimelineService uts;
	
	@Autowired
    private DBFileStorageService dbFileStorageService;
	
	
	@GetMapping(value = "/users/public/profile/image/{imageid}", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getUserProfileImage(@PathVariable String imageid)  {
		
		return dbFileStorageService.getFile(imageid).getFileData();

	}

	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/users/timeline/{userid}")
	ResponseEntity<List<UserTimelineItem>> getUserTimeline(@PathVariable String userid) {
		try {
			List<UserTimelineItem> uteList = uts.getTimeline(userid);
			return new ResponseEntity<List<UserTimelineItem>>(uteList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<UserTimelineItem>>(new ArrayList<UserTimelineItem>(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/users/followers/{userid}")
	ResponseEntity<UserFollowers> getUserFollowers(@PathVariable String userid) {
		try {
			UserFollowers userFollowers = uas.followersCount(userid);
			return new ResponseEntity<UserFollowers>(userFollowers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<UserFollowers>(new UserFollowers(userid, 0L), HttpStatus.BAD_REQUEST);
		}
		
	}

	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/users/following/{userid}")
	ResponseEntity<UserFollowees> getUserFollowees(@PathVariable String userid) {
		try {
			UserFollowees userFollowees = uas.followingCount(userid);
			return new ResponseEntity<UserFollowees>(userFollowees, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<UserFollowees>(new UserFollowees(userid, 0L), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/users/profile/{username}")
	ResponseEntity<User> getUserProfile(@PathVariable("username") String username) {
		try {
			
			User user = uactsvc.getUserDocument(username);
			if(user == null) {
				user = kus.getUserPrfoileData(username);
				uactsvc.createUserDocument(user);
			}
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<User>(new User(), HttpStatus.BAD_REQUEST);
		}
		
	}

//	@GetMapping("/test/users/profile/{username}")
//	ResponseEntity<User> getUserProfileTest(@PathVariable("username") String username) {
//		try {
//			
//			User user = uactsvc.getUserDocument(username);
//			if(user == null) {
//				throw new RuntimeException("User not found with username: " + username);
//			}
//			return new ResponseEntity<User>(user, HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<User>(new User(), HttpStatus.BAD_REQUEST);
//		}
//		
//	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/users/logins/reminders")
	ResponseEntity<List<String>> loginReminderUsers() {
		
		List<String> users = new ArrayList<String>();
		users.add("100");
		users.add("101");
		users.add("102");
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@PreAuthorize("#oauth2.hasScope('user-service-write')")
	@GetMapping("/users/logins/write")
	ResponseEntity<List<String>> loginWriteMethod() {
		
		List<String> users = new ArrayList<String>();
		users.add("200");
		users.add("201");
		users.add("202");
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@PreAuthorize("#oauth2.hasScope('user-service-write-restricted')")
	@GetMapping("/users/logins/write-restricted")
	ResponseEntity<List<String>> loginWriteRestrictedMethod() {
		
		List<String> users = new ArrayList<String>();
		users.add("300");
		users.add("301");
		users.add("302");
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
}
