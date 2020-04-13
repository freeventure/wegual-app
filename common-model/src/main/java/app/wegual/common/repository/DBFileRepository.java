package app.wegual.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.wegual.common.model.DBFile;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {

}
