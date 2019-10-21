package app.wegual.poc.springdatamysql;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.wegual.poc.common.model.Beneficiary;
import app.wegual.poc.common.model.Pledge;
import app.wegual.poc.common.model.User;

@RepositoryRestResource(collectionResourceRel = "pledges", path = "pledges")
public interface PledgeRepository extends PagingAndSortingRepository<Pledge, Long> {

	List<Beneficiary> findAllByBeneficiary(String name);
	
	List<Beneficiary> findAllByPledgedBy(User pledgedBy);
	
	//List<Pledge> findAllByBeneficiary(Long id);
}