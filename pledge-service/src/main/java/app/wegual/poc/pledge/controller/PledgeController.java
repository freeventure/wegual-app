package app.wegual.poc.pledge.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.wegual.poc.common.model.Pledge;
import app.wegual.poc.pledge.service.PledgeService;



@RestController
@RequestMapping("/pledge")
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
	
	@GetMapping("/pledgeTotal")
	public long pledgesTotal() throws IOException {
		System.out.println("Inside pledge controller");
		return (pledgeService.pledgeTotal());
	}
	
	@GetMapping("/usersTotalForBeneiciary/{id}")
	public long usersTotalForBeneiciary(@PathVariable String id) throws IOException{
		System.out.println("Inside pledge controller");
		return (pledgeService.usersTotalForBeneiciary(id));
	}
	
	@GetMapping("/giveUpTotalForBeneiciary/{id}")
	public long giveUpTotalForBeneiciary(@PathVariable String id) throws IOException{
		System.out.println("Inside pledge controller");
		return (pledgeService.giveUpTotalForBeneiciary(id));
	}
	
	@GetMapping("/pledgesTotalForBeneiciary/{id}")
	public long pledgesTotalForBeneiciary(@PathVariable String id) throws IOException{
		System.out.println("Inside pledge controller");
		return (pledgeService.pledgesTotalForBeneiciary(id));
	}
	
	@GetMapping("/amountTotalForBeneficiary/{id}")
	public double amountTotalForBeneficiary(@PathVariable String id) throws IOException{
		System.out.println("Inside pledge controller");
		return (pledgeService.amountTotalForBeneficiary(id));
	}
}