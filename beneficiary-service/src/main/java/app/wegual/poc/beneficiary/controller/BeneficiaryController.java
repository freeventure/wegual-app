package app.wegual.poc.beneficiary.controller;

import java.io.IOException;

import org.elasticsearch.search.aggregations.Aggregations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.wegual.poc.beneficiary.clients.PledgeServiceClient;
import app.wegual.poc.beneficiary.service.BeneficiaryService;
import app.wegual.poc.common.model.Beneficiary;

@RestController
@RequestMapping("/beneficiary")
public class BeneficiaryController {
	
	@Autowired
	private BeneficiaryService beneficiaryService;
	
	@Autowired 
	private PledgeServiceClient pledgeServiceClient;
	
	@PostMapping(value = "/save")
	public String saveBeneficiary(@RequestBody Beneficiary ben) throws IOException{
		System.out.println("Inside beneficiary controller");
		beneficiaryService.save(ben);
		return("Beneficiary Craeted successfully!");
	}
	
	@GetMapping(value = "/beneficiaryTotal")
	public long beneficiaryTotal() throws IOException{
		System.out.println("Inside beneficiary controller");
		return(beneficiaryService.beneficiaryTotal());
	}
	
	@GetMapping(value = "/usersTotalForBeneficiary/{id}")
	public long usersTotalForBeneficiary(@PathVariable String id) throws IOException{
		System.out.println("Inside beneficiary controller");
		return(pledgeServiceClient.getUsersTotalForBeneiciary(id));
	}
	
	@GetMapping(value = "/giveUpTotalForBeneficiary/{id}")
	public long giveUpTotalForBeneficiary(@PathVariable String id) throws IOException{
		System.out.println("Inside beneficiary controller");
		return(pledgeServiceClient.getGiveUpTotalForBeneiciary(id));
	}
	
	@GetMapping(value = "/pledgesTotalForBeneficiary/{id}")
	public long pledgesTotalForBeneficiary(@PathVariable String id) throws IOException{
		System.out.println("Inside beneficiary controller");
		return(pledgeServiceClient.getPledgesTotalForBeneiciary(id));
	}
	
	@GetMapping(value = "/amountTotalForBeneficiary/{id}")
	public void amountTotalForBeneficiary(@PathVariable String id) throws IOException{
		System.out.println("Inside beneficiary controller");
		pledgeServiceClient.getAmountTotalForBeneficiary(id);
	}
	
	@GetMapping(value = "/findBeneficiaryFollowers/{id}")
	public void findBeneficiaryFollowers(@PathVariable String id) throws IOException{
		System.out.println("Inside beneficiary controller");
		beneficiaryService.findBeneficiaryFollowers(id);
	}
	
	@GetMapping(value = "/topBeneficiariesByPledge")
	public Aggregations topBeneficiariesByPledge() throws IOException{
		System.out.println("Inside beneficiary controller");
		return (beneficiaryService.topBeneficiariesByPledge());
	}
}

