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
@JsonSubTypes({
    @JsonSubTypes.Type(value = UserTimelineItem.class, name = "usertimeline"),
    @JsonSubTypes.Type(value = BeneficiaryTimelineItem.class, name = "beneficiarytimeline")
 })

public class TimelineItem<T, ATT, AT> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected T actorId;
	
	protected String detail;
	
	protected TimelineItemDetailActions detailActions;

	protected AT actionType;
	
	protected Long actionDate;
	
	protected ActionTarget<T, ATT> target;
	protected ActionTarget<T, ATT> actionObject;
	
	public TimelineItem(T actorId, ActionTarget<T, ATT> actionObject, ActionTarget<T, ATT> actionTarget, AT actionType) {
		this.actorId = actorId;
		this.actionObject = actionObject;
		this.target = actionTarget;
		this.actionType = actionType;
	}
}
