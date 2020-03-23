package com.wegual.scheduler.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("user-service")
public interface UserServiceClient {

	    @RequestMapping(method = RequestMethod.GET, value = "/users/logins/reminders")
	    List<String> getUserLoginReminders(@RequestHeader(value = "Authorization", required = true) String language);

}
