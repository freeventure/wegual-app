package app.wegual.poc.springdatamysql;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.wegual.poc.common.model.Beneficiary;
import app.wegual.poc.common.model.GiveUp;
import app.wegual.poc.common.model.Pledge;
import app.wegual.poc.common.model.User;

@RepositoryRestResource(collectionResourceRel = "pledges", path = "pledges")
public interface PledgeRepository extends PagingAndSortingRepository<Pledge, Long> {

	//List<Beneficiary> findAllByBeneficiary(String name);
	
	@Query(value="Select count(*) FROM Pledge")
	Long countAllPLedges();
	
	@Query(value="SELECT p.beneficiary FROM Pledge p WHERE p.pledgedBy=:pledgedBy")
	List<Beneficiary> findAllBeneficiariesPledgedBy(@Param("pledgedBy") User pledgedBy);
	
	@Query(value="SELECT count(distinct p.beneficiary) FROM Pledge p WHERE p.pledgedBy=:pledgedBy")
	Long countAllBeneficiariesPledgedBy(@Param("pledgedBy") User pledgedBy);
	
	@Query(value="SELECT p.pledgedBy FROM Pledge p WHERE p.beneficiary=:beneficiary")
	List<User> findAllUsersByBeneficiary(@Param("beneficiary") Beneficiary beneficiary);
	
	@Query(value="SELECT count(distinct p.pledgedBy) FROM Pledge p WHERE p.beneficiary=:beneficiary")
	Long countAllUsersByBeneficiary(@Param("beneficiary") Beneficiary beneficiary);
	
	List<Pledge> findAllByBeneficiary(Beneficiary beneficiary);
	 
	@Query(value="SELECT count(p) FROM Pledge p where p.beneficiary=:beneficiary")
	Long countAllByBeneficiary(@Param("beneficiary") Beneficiary beneficiary);
	
	@Query(value="SELECT sum(p.amount) FROM Pledge p WHERE p.beneficiary=:beneficiary")
	double findTotalForBeneficiary(@Param("beneficiary") Beneficiary beneficiary);
	
	@Query(value="SELECT p.pledgedBy FROM Pledge p WHERE p.giveUp=:giveUp")
	List<User> findAllUsersByGiveUp(@Param("giveUp") GiveUp giveUp);
	
	@Query(value="SELECT count(distinct p.pledgedBy) FROM Pledge p WHERE p.giveUp=:giveUp")
	Long countAllUsersByGiveUp(@Param("giveUp") GiveUp giveUp);
	
	@Query(value="SELECT p.beneficiary FROM Pledge p where p.giveUp=:giveUp")
	List<Beneficiary> findAllBeneficiaryByGiveUp(GiveUp giveUp);
	
	@Query(value="SELECT count(distinct p.beneficiary) FROM Pledge p where p.giveUp=:giveUp")
	Long countAllBeneficiaryByGiveUp(GiveUp giveUp);
	
	List<Pledge> findAllByGiveUp(GiveUp giveUp);
	
	@Query(value="SELECT count(p) FROM Pledge p WHERE p.giveUp=:giveUp")
	Long countAllByGiveUp(GiveUp giveUp);
	
	//List<Pledge> findAllByBeneficiary(Long id);
}