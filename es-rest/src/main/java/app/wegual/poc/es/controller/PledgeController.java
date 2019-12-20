package app.wegual.poc.es.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.wegual.poc.es.model.Pledge;
import app.wegual.poc.es.service.PledgeService;

@RestController
@RequestMapping("pledge")
public class PledgeController {
	
	@Autowired
	private PledgeService pledgeService;
	
	@PostMapping(value="/save")
	public void savePledge(@RequestBody Pledge pledge){
		try {
			System.out.println("Inside pledge controller");
			pledgeService.save(pledge);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
