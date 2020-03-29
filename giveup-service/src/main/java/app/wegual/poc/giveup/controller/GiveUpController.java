package app.wegual.poc.giveup.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.wegual.poc.common.model.GiveUp;
import app.wegual.poc.giveup.service.GiveUpService;



@RestController
@RequestMapping("giveup")
public class GiveUpController {
	
	@Autowired
	private GiveUpService giveUpService;
	
	@PostMapping(value="/save")
	public String saveUser(@RequestBody GiveUp giveUp){
		try {
			System.out.println("Inside user controller");
			giveUpService.save(giveUp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return("GiveUp created sucessfully!");
	}
	
	@GetMapping("/giveUpTotal")
	public long userTotal() throws IOException {
		System.out.println("Inside giveUp controller");
		return(giveUpService.giveUpTotal());
	}
	
	@GetMapping("pledgesTotalForGiveUp/{id}")
	public long pledgesTotalForGiveUp(@PathVariable String id) throws IOException {
		System.out.println("Inside giveUp controller");
		return(giveUpService.pledgesTotalForGiveUp(id));
		
	}
	
	@GetMapping("usersTotalForGiveUp/{id}")
	public long usersTotalForGiveUp(@PathVariable String id) throws IOException {
		System.out.println("Inside giveUp controller");
		return(giveUpService.usersTotalForGiveup(id));
		
	}
	
	@GetMapping("beneficiaryTotalForGiveup/{id}")
	public long beneficiaryTotalForGiveup(@PathVariable String id) throws IOException {
		System.out.println("Inside giveUp controller");
		return(giveUpService.beneficiaryTotalForGiveup(id));
		
	}

}
