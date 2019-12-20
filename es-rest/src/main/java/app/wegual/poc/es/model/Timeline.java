package app.wegual.poc.es.model;

import java.util.Date;
import java.time.Instant;

import org.springframework.stereotype.Component;

@Component
public class Timeline {
	
	private String actorId;
	private String targetId;
	private String operationType;
	private Date timestamp;
	
	public Timeline withActorId(String id){
		this.actorId = id;
		return this;
	}
	
	public Timeline withTargetID(String id) {
		this.targetId = id;
		return this;
	}
	
	public Timeline withOperationType(String opType){
		this.operationType = opType;
		return this;
	}
	
	public Timeline withTimestamp(Date timestamp){
		this.timestamp = timestamp;
		return this;
	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
}
