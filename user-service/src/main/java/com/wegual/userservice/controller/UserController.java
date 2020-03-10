package com.wegual.userservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
	@GetMapping("/users/logins/reminders")
	ResponseEntity<List<String>> loginReminderUsers() {
		
		List<String> users = new ArrayList<String>();
		users.add("100");
		users.add("101");
		users.add("102");
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

}
