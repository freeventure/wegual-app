package app.wegual.poc.springdatamysql;

import java.sql.Date;
import java.util.List;

import org.aspectj.weaver.ast.And;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.wegual.poc.common.model.Beneficiary;
import app.wegual.poc.common.model.BeneficiaryFollowers;
import app.wegual.poc.common.model.GiveUp;
import app.wegual.poc.common.model.User;
import org.springframework.data.repository.query.Param; 

@RepositoryRestResource(collectionResourceRel = "beneficiaryFollowers", path = "beneficiaryFollowers")
public interface BeneficiaryFollowersRepository extends PagingAndSortingRepository<BeneficiaryFollowers, Long> {
	
	
	@Query(value="SELECT b.follower from BeneficiaryFollowers b WHERE b.beneficiary=:beneficiary")
	List<User> findAllByBeneficiary(@Param("beneficiary") Beneficiary beneficiary);
	
	@Query(value="SELECT count(b.follower) from BeneficiaryFollowers b WHERE b.beneficiary=:beneficiary")
	Long countAllByBeneficiary(@Param("beneficiary") Beneficiary beneficiary);
	
	@Query(value="SELECT b.beneficiary from BeneficiaryFollowers b WHERE b.follower=:follower")
	List<Beneficiary> findAllByFollower(@Param("follower") User follower);
	
	@Query(value="SELECT count(b.beneficiary) from BeneficiaryFollowers b WHERE b.follower=:follower")
	Long countAllByFollower(@Param("follower") User follower);
	
//	@Query("select b from BeneficiaryFollowers b where b.beneficiary=:beneficiary and b.follow_date<=:follow_date")	
//	List<BeneficiaryFollowers> findAllByBeneficiaryAndfollow_date(@Param("beneficiary") Beneficiary beneficiary, @Param("follow_date") Date follow_date);
	
	@Query(value="SELECT b.follower FROM BeneficiaryFollowers b WHERE b.beneficiary=:beneficiary AND TIMESTAMPDIFF(day, b.follow_date, now()) <= :days")
	List<User> findAllBeneficiaryFolloweeInLastDays(@Param("days") int days, @Param("beneficiary") Beneficiary beneficiary);
	
	@Query(value="SELECT b.beneficiary FROM BeneficiaryFollowers b WHERE b.follower=:follower AND TIMESTAMPDIFF(day, b.follow_date, now()) <= :days")
	List<Beneficiary> findAllUserBeneficiaryFollowersInLastDays(@Param("days") int days, @Param("follower") User follower);
	
}
