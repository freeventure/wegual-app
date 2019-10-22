package app.wegual.poc.springdatamysql;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.wegual.poc.common.model.GiveUp;
import app.wegual.poc.common.model.User;

@RepositoryRestResource(collectionResourceRel = "giveup", path = "giveup")
public interface GiveUpRepository extends PagingAndSortingRepository<GiveUp, Long> {
	
	@Query(value="Select count(*) FROM GiveUp")
	Long countAllGiveUp();
	
	@Query(value="SELECT sum(amount) FROM GiveUp GROUP BY currency")
	Double countTotalAmount();
	
	GiveUp findByName(String name);
	
	List<GiveUp> findByCreatedBy(User createdBy);
}
