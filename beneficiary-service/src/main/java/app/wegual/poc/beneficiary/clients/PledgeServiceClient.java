package app.wegual.poc.beneficiary.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("pledge-service")
public interface PledgeServiceClient {
	
	 @RequestMapping(method = RequestMethod.GET, value = "/pledge/usersTotalForBeneiciary/{id}")
	    Long getUsersTotalForBeneiciary(@PathVariable("id") String id);
	 @RequestMapping(method = RequestMethod.GET, value = "/pledge/giveUpTotalForBeneiciary/{id}")
	    Long getGiveUpTotalForBeneiciary(@PathVariable("id") String id);
	 @RequestMapping(method = RequestMethod.GET, value = "/pledge/pledgesTotalForBeneiciary/{id}")
	    Long getPledgesTotalForBeneiciary(@PathVariable("id") String id);
	 @RequestMapping(method = RequestMethod.GET, value = "/pledge/amountTotalForBeneiciary/{id}")
	    Double getAmountTotalForBeneficiary(@PathVariable("id") String id);
}