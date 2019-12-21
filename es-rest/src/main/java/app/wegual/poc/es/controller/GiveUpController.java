package app.wegual.poc.es.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.wegual.poc.es.model.GiveUp;
import app.wegual.poc.es.service.GiveUpService;

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
		System.out.println("Inside user controller");
		return(giveUpService.giveUpTotal());
	}

}
