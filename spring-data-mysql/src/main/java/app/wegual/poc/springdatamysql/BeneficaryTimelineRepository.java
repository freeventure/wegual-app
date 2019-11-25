package app.wegual.poc.springdatamysql;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import app.wegual.poc.common.model.BeneficiaryTimeline;

public interface BeneficaryTimelineRepository extends CrudRepository<BeneficiaryTimeline, String> {  
	
	@Query(value = "SELECT BeneficiaryTimeline bt where bt.beneficiaryId =:benId")
	List<BeneficiaryTimeline> findAllByBeneficiaryId(@Param("benId") String benId);

}
