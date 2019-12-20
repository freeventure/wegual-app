package app.wegual.poc.es.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.wegual.poc.es.model.BeneficiaryFollowers;
import app.wegual.poc.es.model.GiveUpFollowers;
import app.wegual.poc.es.model.User;
import app.wegual.poc.es.model.UserFollowers;
import app.wegual.poc.es.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping(value="/save/{id}")
	public void saveUser(@PathVariable String id,  @RequestBody User user){
		try {
			System.out.println("Inside user controller");
			userService.save(id , user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@PostMapping(value="/followUser")
	public void followUser(@RequestBody UserFollowers userFollower) throws IOException {
		userService.followUser(userFollower);
	}
	
	@PostMapping(value="/followBeneficiary")
	public void followBeneficiary(@RequestBody BeneficiaryFollowers benFollowers) throws IOException {
		userService.followBeneficiary(benFollowers);
	}
	
	@PostMapping(value="/followGiveUp")
	public void followGiveUp(@RequestBody GiveUpFollowers benFollowers) throws IOException {
		userService.followGiveUp(benFollowers);
	}
}
