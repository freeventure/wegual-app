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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wegual.userservice.analytics.UserAnalyticsService;
import com.wegual.userservice.service.KeycloakUserService;
import com.wegual.userservice.service.UserActionsService;
import com.wegual.userservice.service.UserFeedService;
import com.wegual.userservice.service.UserTimelineService;

import app.wegual.common.model.FeedItem;
import app.wegual.common.model.GenericItem;
import app.wegual.common.model.TokenStatus;
import app.wegual.common.model.User;
import app.wegual.common.model.UserDetails;
import app.wegual.common.model.PledgeFeedItem;
import app.wegual.common.model.UserTimelineItem;
import app.wegual.common.rest.model.UserFollowees;
import app.wegual.common.rest.model.UserFollowers;
import app.wegual.common.service.DBFileStorageService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	UserFeedService ufs;
	
	@Autowired
    private DBFileStorageService dbFileStorageService;

	@GetMapping(value="/test/keycloak/activate/account/{userid}")
	ResponseEntity<String> activateKeycloakAcount(@PathVariable String userid) {
		try {
			kus.activateAccount(userid);
			return new ResponseEntity<String>("Activated", HttpStatus.OK);
		} catch (Exception e) {
		
			log.error("Error occured activating user account", e);
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
	}
	

//	@GetMapping(value="/test/keycloak/account")
//	ResponseEntity<String> createKeycloakAcount() {
//		try {
//			String id = kus.createInactiveUserAccount();
//			return new ResponseEntity<String>(id, HttpStatus.OK);
//		} catch (Exception e) {
//		
//			log.error("Error occured", e);
//			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
//		}
//	}
    @RequestMapping(method = RequestMethod.POST, value = "/users/public/email/token/verify", consumes = "application/x-www-form-urlencoded")
    TokenStatus verifyUserToken(@RequestParam("token") String token, @RequestParam("tokenId") String tokenId) {
    		TokenStatus ts = uactsvc.verifyTokenById(tokenId, token);
    		log.info("Token verification status by service: " + ts.toString());
    		return ts;
    }
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/users/public/email/token", consumes = "application/json")
	ResponseEntity<String> getUserEmailVerifyToken(@RequestHeader("X-aux") String secret, @RequestBody User user) {
		try {
			String id = uactsvc.sendVerificationToken(secret, user);
			log.info("Received user id: " + id + " for token");
			return new ResponseEntity<String>(id, HttpStatus.OK);
		} catch (Exception e) {
		
			log.error("Error occured", e);
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/users/public/profile/image/{imageid}", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getUserProfileImage(@PathVariable String imageid)  {
		return dbFileStorageService.getFile(imageid).getFileData();
	}

	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/users/timeline/{userid}")
	ResponseEntity<List<UserTimelineItem>> getUserTimeline(@PathVariable String userid) {
		try {
			List<UserTimelineItem> uteList = uts.getTimeline(userid);
			log.info("Inside user controller, timeline hits: " + uteList.size());
			return new ResponseEntity<List<UserTimelineItem>>(uteList, HttpStatus.OK);
		} catch (Exception e) {
			log.error(""+e);
			return new ResponseEntity<List<UserTimelineItem>>(new ArrayList<UserTimelineItem>(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/users/scrollabletimeline/{userid}/{timestamp}")
	ResponseEntity<List<UserTimelineItem>> getScrollableUserTimeline(@PathVariable("userid") String userId, @PathVariable("timestamp") long timestamp) {
		try {
			List<UserTimelineItem> uteList = uts.getScrollableTimeline(userId, timestamp);
			log.info("Inside user controller, timeline hits: " + uteList.size());
			return new ResponseEntity<List<UserTimelineItem>>(uteList, HttpStatus.OK);
		} catch (Exception e) {
			log.error(""+e);
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
			log.error(""+e);
			return new ResponseEntity<UserFollowers>(new UserFollowers(userid, 0L), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/users/feed/{userid}")
	ResponseEntity<List<PledgeFeedItem>> getUserFeed(@PathVariable String userid) {
		try {
			List<PledgeFeedItem> feed = ufs.getFeed(userid);
			return new ResponseEntity<List<PledgeFeedItem>>(feed, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error in getting feed", e);
			return new ResponseEntity<List<PledgeFeedItem>>(new ArrayList<PledgeFeedItem>(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/users/following/{userid}")
	ResponseEntity<UserFollowees> getUserFollowees(@PathVariable String userid) {
		try {
			UserFollowees userFollowees = uas.followingCount(userid);
			return new ResponseEntity<UserFollowees>(userFollowees, HttpStatus.OK);
		} catch (Exception e) {
			log.error(""+e);
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
				user.setUpdatedTimestamp(System.currentTimeMillis());
				uactsvc.createUserDocument(user);
			}
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			log.error(""+e);
			return new ResponseEntity<User>(new User(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@PostMapping("/users/save/details")
	void saveUserDetails(@RequestBody UserDetails ud) {
		try {
			uactsvc.saveUserDetails(ud);
		} catch (Exception e) {
			log.error("Error saving user details : "+ e);
			e.printStackTrace();
		}
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/users/checktwitterauth")
	@ResponseBody
	Long checkTwitterAuth(@RequestParam("userId") String userId) {
		System.out.println("Inside user service, checking twitter auth");
		try {
			Long tmp = uactsvc.checkTwitterOauthToken(userId);
			System.out.println(tmp);
			return tmp;
		} catch (Exception e) {
			log.error("Error saving user details : "+ e);
			e.printStackTrace();
		}
		return (long) 0;
	}
	
	@PreAuthorize("#oauth2.hasScope('user-service')")
	@GetMapping("/users/suggest/{name}")
	List<GenericItem<String>> suggestUserByName(@PathVariable String name) {
		try {
			return uactsvc.getSuggestionSearch(name);
		} catch (Exception e) {
			log.error("Error saving user details : "+ e);
			e.printStackTrace();
		}
		return new ArrayList<GenericItem<String>>();
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
