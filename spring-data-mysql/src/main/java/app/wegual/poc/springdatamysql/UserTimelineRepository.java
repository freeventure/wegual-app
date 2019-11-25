package app.wegual.poc.springdatamysql;

import org.springframework.data.repository.CrudRepository;

import app.wegual.poc.common.model.UserTimeline;

public interface UserTimelineRepository extends CrudRepository<UserTimeline, String> {

}
