package app.wegual.poc.scheduler.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("user-service")
public interface UserServiceClient {

	@RequestMapping(method=RequestMethod.GET, value="/user/findInactiveUsers")
	void findInactiveUsers(@RequestHeader(value = "Authorization", required = true) String token);
}
