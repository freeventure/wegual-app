package app.wegual.poc.springdatamysql;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.wegual.poc.common.model.Beneficiary;
import app.wegual.poc.common.model.Pledge;
import app.wegual.poc.common.model.User;

@RepositoryRestResource(collectionResourceRel = "pledges", path = "pledges")
public interface PledgeRepository extends PagingAndSortingRepository<Pledge, Long> {

	//List<Beneficiary> findAllByBeneficiary(String name);
	
	@Query(value="SELECT p.beneficiary FROM Pledge p WHERE p.pledgedBy=:pledgedBy")
	List<Beneficiary> findAllBeneficiariesPledgedBy(@Param("pledgedBy") User pledgedBy);
	
	@Query(value="SELECT p.pledgedBy FROM Pledge p WHERE p.beneficiary=:beneficiary")
	List<User> findAllUsersByBeneficiary(@Param("beneficiary") Beneficiary beneficiary);
	
	List<Pledge> findAllByBeneficiary(Beneficiary beneficiary);
	 
	@Query(value="SELECT sum(p.amount) FROM Pledge p WHERE p.beneficiary=:beneficiary")
	double findTotalForBeneficiary(@Param("beneficiary") Beneficiary beneficiary);
	
	//List<Pledge> findAllByBeneficiary(Long id);
}