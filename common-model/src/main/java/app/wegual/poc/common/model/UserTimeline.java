package app.wegual.poc.common.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;

import org.hibernate.annotations.CreationTimestamp;

public class UserTimeline implements Serializable {
	
	@Column(nullable=false)
	private String userId;
	
	@Column(nullable=false)
	private String objectId;
	
	@CreationTimestamp
   	private Timestamp timestamp;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
