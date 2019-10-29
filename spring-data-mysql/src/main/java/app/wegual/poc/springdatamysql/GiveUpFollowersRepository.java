package app.wegual.poc.springdatamysql;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.wegual.poc.common.model.GiveUp;
import app.wegual.poc.common.model.GiveUpFollower;
import app.wegual.poc.common.model.User;

@RepositoryRestResource(collectionResourceRel = "giveupfollowers", path = "giveupfollowers")
public interface GiveUpFollowersRepository extends PagingAndSortingRepository<GiveUpFollower, Long> {
	
	@Query(value="SELECT g.follower from GiveUpFollower g WHERE g.giveup=:giveUp")
	List<User> findAllByGiveUp(@Param("giveUp") GiveUp giveUp);
	
	@Query(value="SELECT count(g.follower) from GiveUpFollower g WHERE g.giveup=:giveUp")
	Long countAllByGiveUp(@Param("giveUp") GiveUp giveUp);
	
	@Query(value="SELECT g.giveup from GiveUpFollower g WHERE g.follower=:follower")
	List<GiveUp> findAllByFollower(@Param("follower") User follower);
	
	@Query(value="SELECT count(g.giveup) from GiveUpFollower g WHERE g.follower=:follower")
	Long countAllByFollower(@Param("follower") User follower);
	
	@Query(value="SELECT g FROM GiveUpFollower g WHERE g.follower=:follower AND TIMESTAMPDIFF(day, g.follow_date, now()) <= :days")
	List<User> findAllGiveUpFollowersInLastDays(@Param("days") int days, @Param("follower") User follower);

}
