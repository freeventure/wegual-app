package app.wegual.poc.common.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class UserTimeline implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	
	@Column(nullable=false)
	private String userId;
	
	@Column(nullable=true)
	private String objectId;
	
	@Column(nullable=false)
	private String operationType;
	
	@CreationTimestamp
   	private Timestamp timestamp;
	
	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public UserTimeline withId(String id) {
		this.userId=id;
		return this;
	}
	
	public UserTimeline withObjectId(String objId) {
		this.objectId=objId;
		return this;
	}
	
	public UserTimeline withTimestamp(Timestamp ts) {
		this.timestamp=ts;
		return this;
	}
	
	public UserTimeline withOperationType(String ot) {
		this.operationType=ot;
		return this;
	}
	
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
