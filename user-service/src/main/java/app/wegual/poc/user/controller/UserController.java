package app.wegual.poc.user.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.wegual.poc.common.model.BeneficiaryFollowers;
import app.wegual.poc.common.model.GiveUpFollowers;
import app.wegual.poc.common.model.User;
import app.wegual.poc.common.model.UserFollowers;
import app.wegual.poc.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping(value="/save")
	public String saveUser(@RequestBody User user){
		try {
			System.out.println("Inside user controller");
			userService.save(user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return("User Created successfully!!");
	}
	
	@GetMapping("/userTotal")
	public long userTotal() throws IOException {
		System.out.println("Inside user controller");
		return(userService.userTotal());
	}
	
	@PostMapping(value="/followUser")
	public String followUser(@RequestBody UserFollowers userFollower) throws IOException {
		System.out.println("Inside user controller");
		userService.followUser(userFollower);
		return("User Followed successfully!!");
	}
	
	@PostMapping(value="/followBeneficiary")
	public String followBeneficiary(@RequestBody BeneficiaryFollowers benFollowers) throws IOException {
		System.out.println("Inside user controller");
		userService.followBeneficiary(benFollowers);
		return("Beneficiary Followed successfully!!");
	}
	
	@PostMapping(value="/followGiveUp")
	public String followGiveUp(@RequestBody GiveUpFollowers benFollowers) throws IOException {
		System.out.println("Inside user controller");
		userService.followGiveUp(benFollowers);
		return("GiveUp Followed successfully!!");
	}
	
	@GetMapping("/findUserFollowers/{id}")
	public long findUserFollowers(@PathVariable String id) throws IOException {
		System.out.println("Inside user controller");
		return(userService.findUserFollowers(id));
	}
	
	@GetMapping("/findUserFollowing/{id}")
	public long findUserFollowing(@PathVariable String id) throws IOException {
		System.out.println("Inside user controller");
		return(userService.findUserFollowing(id));
	}
	@GetMapping("/findInactiveUsers")
	public void findInactiveUsers() throws IOException {
		System.out.println("Inside user controller");
		userService.findInactiveUsers();
	}
}
