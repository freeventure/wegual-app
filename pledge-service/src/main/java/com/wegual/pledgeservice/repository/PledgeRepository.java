package com.wegual.pledgeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.wegual.pledgeservice.model.Pledge;

@RepositoryRestResource(path="pledge")
public interface PledgeRepository extends JpaRepository<Pledge, Long> {
	
}
