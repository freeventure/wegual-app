package app.wegual.common.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeInfo (use = JsonTypeInfo.Id.CLASS, include = As.PROPERTY, property = "classNameExtenral")
@JsonSubTypes ({@Type (value = UserTimelineItem.class, name = "usertimeline")})
public class TimelineItem<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected T actorId;
	
	protected String detail;
	
	protected TimelineItemDetailActions detailActions;

	protected UserActionType userActionType;
	
	protected Long actionDate;
	
	protected ActionTarget<T> target;
	protected ActionTarget<T> actionObject;
	
	public TimelineItem(T actorId, ActionTarget<T> actionObject, ActionTarget<T> actionTarget, UserActionType uat) {
		this.actorId = actorId;
		this.actionObject = actionObject;
		this.target = actionTarget;
		this.userActionType = uat;
	}
}
