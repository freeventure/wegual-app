package com.wegual.userservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
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
