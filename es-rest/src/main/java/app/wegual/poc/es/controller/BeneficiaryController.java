package app.wegual.poc.es.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.wegual.poc.es.model.Beneficiary;
import app.wegual.poc.es.service.BeneficiaryService;
import app.wegual.poc.es.service.PledgeService;

@RestController
@RequestMapping("/beneficiary")
public class BeneficiaryController {
	
	@Autowired
	private BeneficiaryService beneficiaryService;
	
	@Autowired 
	private PledgeService pledgeService;
	
	@PostMapping(value = "/save")
	public void saveBeneficiary(@RequestBody Beneficiary ben) throws IOException{
		System.out.println("Inside beneficiary controller");
		beneficiaryService.save(ben);
	}
	
	@GetMapping(value = "/beneficiaryTotal")
	public void beneficiaryTotal() throws IOException{
		System.out.println("Inside beneficiary controller");
		beneficiaryService.beneficiaryTotal();
	}
	
	@GetMapping(value = "/usersTotalForBeneficiary/{id}")
	public void usersTotalForBeneficiary(@PathVariable String id) throws IOException{
		System.out.println("Inside beneficiary controller");
		pledgeService.usersTotalForBeneiciary(id);
	}
	
	@GetMapping(value = "/pledgesTotalForBeneficiary/{id}")
	public void pledgesTotalForBeneficiary(@PathVariable String id) throws IOException{
		System.out.println("Inside beneficiary controller");
		pledgeService.pledgesTotalForBeneiciary(id);
	}
	
	@GetMapping(value = "/amountTotalForBeneficiary/{id}")
	public void amountTotalForBeneficiary(@PathVariable String id) throws IOException{
		System.out.println("Inside beneficiary controller");
		pledgeService.amountTotalForBeneficiary(id);
	}
}
