package app.wegual.poc.springdatamysql;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.wegual.poc.common.model.GiveUp;
@RepositoryRestResource(collectionResourceRel = "giveup", path = "giveup")
public interface GiveUpRepository extends PagingAndSortingRepository<GiveUp, Long> {
	
}
