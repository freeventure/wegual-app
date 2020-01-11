package app.wegual.poc.es.model;

import java.util.Date;
import java.time.Instant;

import org.springframework.stereotype.Component;

@Component
public class Timeline {
	
	private String actionId;
	private String actionType;
	private String actorId;
	private String actorName;
	private String actorType;
	private String targetId;
	private String targetName;
	private String targetType;
	private String optionalTargetId;
	private String optionalTargetName;
	private String optionalTargetType;
	private Date timestamp;
	
//	public Timeline withAction(String actionId, String actionType){
//		this.actionId = actionId;
//		this.actionType = actionType;
//		return this;
//	}
	
	public Timeline withActor(String id, String name, String type){
		this.actorId = id;
		this.actorName = name;
		this.actorType = type;
		return this;
	}
	
	public Timeline withTarget(String id, String name, String type){
		this.targetId = id;
		this.targetName = name;
		this.targetType = type;
		return this;
	}
	
	public Timeline withOptionalTarget(String id, String name, String type){
		this.optionalTargetId = id;
		this.optionalTargetName = name;
		this.optionalTargetType = type;
		return this;
	}
	
	public Timeline withTimestamp(Date timestamp){
		this.timestamp = timestamp;
		return this;
	}
	public Timeline withActionType(String action){
		this.actionType = action;
		return this;
	}
	public Timeline withActionId(String id) {
		this.actionId = id;
		return this;
	}

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public String getActorName() {
		return actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public String getActorType() {
		return actorType;
	}

	public void setActorType(String actorType) {
		this.actorType = actorType;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getOptionalTargetId() {
		return optionalTargetId;
	}

	public void setOptionalTargetId(String optionalTargetId) {
		this.optionalTargetId = optionalTargetId;
	}

	public String getOptionalTargetName() {
		return optionalTargetName;
	}

	public void setOptionalTargetName(String optionalTargetName) {
		this.optionalTargetName = optionalTargetName;
	}

	public String getOptionalTargetType() {
		return optionalTargetType;
	}

	public void setOptionalTargetType(String optionalTargetType) {
		this.optionalTargetType = optionalTargetType;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	
	
}
