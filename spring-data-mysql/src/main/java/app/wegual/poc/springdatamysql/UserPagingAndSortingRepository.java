package app.wegual.poc.springdatamysql;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.wegual.poc.common.model.User;

//This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
//CRUD refers Create, Read, Update, Delete

@RepositoryRestResource(collectionResourceRel = "userpaged", path = "userpaged")
public interface UserPagingAndSortingRepository extends PagingAndSortingRepository<User, Long> {

	List<User> findByEmail(String email);
	
	List<User> findAllByName(String name, Pageable pageable);
}