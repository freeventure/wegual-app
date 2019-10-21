package app.wegual.poc.springdatamysql;

import java.sql.Date;
import java.util.List;

import org.aspectj.weaver.ast.And;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.wegual.poc.common.model.Beneficiary;
import app.wegual.poc.common.model.BeneficiaryFollowers;
import app.wegual.poc.common.model.User;
import org.springframework.data.repository.query.Param; 

@RepositoryRestResource(collectionResourceRel = "beneficiaryFollowers", path = "beneficiaryFollowers")

public interface BeneficiaryFollowersRepository extends PagingAndSortingRepository<BeneficiaryFollowers, Long> {
	
	List<User> findAllByFollowee(Beneficiary followee);
	
@Query("select b from BeneficiaryFollowers b where b.followee=:followee and b.follow_date<=:follow_date")	
	List<BeneficiaryFollowers> findAllByFolloweeAndfollow_date(@Param("followee") Beneficiary followee, @Param("follow_date") Date follow_date);
}
