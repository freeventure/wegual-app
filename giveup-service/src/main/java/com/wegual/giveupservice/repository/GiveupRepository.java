package com.wegual.giveupservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.wegual.giveupservice.model.Giveup;

@RepositoryRestResource(path="giveup")
public interface GiveupRepository extends JpaRepository<Giveup, Long> {

}
