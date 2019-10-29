package app.wegual.poc.springdatamysql;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.wegual.poc.common.model.Beneficiary;
import app.wegual.poc.common.model.BeneficiaryFollowers;
import app.wegual.poc.common.model.User;
import app.wegual.poc.common.model.UserFollowers;

@RepositoryRestResource(collectionResourceRel = "userFollowers", path = "userFollowers")
public interface UserFollowersRepository extends PagingAndSortingRepository<UserFollowers, Long> {
	
	@Query(value="SELECT u.follower from UserFollowers u WHERE u.followee=:followee")
	List<User> findAllByFollowee(@Param("followee") User followee);
	
	@Query(value="SELECT count(u.follower) from UserFollowers u WHERE u.followee=:followee")
	Long countAllByFollowee(@Param("followee") User followee);
	
	@Query(value="SELECT u FROM UserFollowers u WHERE u.followee=:followee AND TIMESTAMPDIFF(day, u.follow_date, now()) <= :days")
	List<User> findAllUserFollowedInLastDays(@Param("days") int days, @Param("followee") User followee);
	
	@Query(value="SELECT u FROM UserFollowers u WHERE u.follower=:follower AND TIMESTAMPDIFF(day, u.follow_date, now()) <= :days")
	List<User> findAllUserFollowingInLastDays(@Param("days") int days, @Param("follower") User follower);
	
}
