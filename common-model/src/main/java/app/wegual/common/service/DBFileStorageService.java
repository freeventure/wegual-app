package app.wegual.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.wegual.common.model.DBFile;
import app.wegual.common.repository.DBFileRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DBFileStorageService {

	@Autowired
    private DBFileRepository dbFileRepository;
	
	public DBFile storeFile(String fileType, byte[] fileData) {
		try {

            DBFile dbFile = new DBFile(null, fileType, fileData);

            return dbFileRepository.save(dbFile);
        } catch (Exception ex) {
        	log.error("Unable to save file", ex);
            throw new IllegalStateException("Could not store file " + fileType);
        }
	}
	
	public DBFile getFile(String fileId) {
		return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("File not found with id " + fileId));
	}
}
