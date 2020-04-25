package app.wegual.poc.springdatamysql;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.wegual.common.model.Beneficiary;
import app.wegual.common.model.User;

//This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
//CRUD refers Create, Read, Update, Delete

@RepositoryRestResource(collectionResourceRel = "beneficiary", path = "beneficiary")
public interface BeneficiaryRepository extends PagingAndSortingRepository<Beneficiary, Long> {
	
	@Query(value="Select count(*) FROM Beneficiary")
	Long countAllBeneficiary();

	Beneficiary findByUrl(String url);
	
	Beneficiary findByName(String name);
	
	List<Beneficiary> findAllByOwner(User owner);
}