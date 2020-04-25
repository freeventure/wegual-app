package app.wegual.poc.springdatamysql;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.wegual.common.model.User;
import app.wegual.common.rest.model.UserFollowers;


@RepositoryRestResource(collectionResourceRel = "userFollowers", path = "userFollowers")
public interface UserFollowersRepository extends PagingAndSortingRepository<UserFollowers, Long> {
	
	@Query(value="SELECT u.follower from UserFollowers u WHERE u.followee=:followee")
	List<User> findAllByFollowee(@Param("followee") User followee);
	
	@Query(value="SELECT count(u.follower) from UserFollowers u WHERE u.followee=:followee")
	Long countAllByFollowee(@Param("followee") User followee);
	
}
