package com.wegual.webapp.message;

import app.wegual.common.model.ActionTarget;
import app.wegual.common.model.GenericActionTarget;
import app.wegual.common.model.TimelineItem;
import app.wegual.common.model.UserActionTargetType;
import app.wegual.common.model.UserActionType;
import app.wegual.common.model.UserTimelineItem;

public class LoginTimelineItemBuilder extends TimelineItem<String> {
	
	public LoginTimelineItemBuilder(String actorId, ActionTarget<String> actionObject,
			ActionTarget<String> actionTarget, UserActionType uat) {
		super(actorId, actionObject, actionTarget, uat);
	}

	public LoginTimelineItemBuilder() {
		super(null, null, null, UserActionType.LOGIN);
		detail = "";
		actionObject = new GenericActionTarget(UserActionTargetType.LOGIN, "", "", "", "");
		target = new GenericActionTarget(UserActionTargetType.USER, "", "", "", "");
		
	}
	
	public LoginTimelineItemBuilder userId(String userId) {
		GenericActionTarget gat = (GenericActionTarget)actionObject;
		this.actorId = userId;
		gat.setId(actorId);
		gat.setPermalink("/users/" + actorId);
		
		gat = (GenericActionTarget)target;
		gat.setId(actorId);
		gat.setPermalink("/users/" + actorId);
		return this;
	}
	
	public LoginTimelineItemBuilder userName(String name) {
		GenericActionTarget gat = (GenericActionTarget)actionObject;
		gat.setName(name);

		gat = (GenericActionTarget)target;
		gat.setName(name);
		return this;
	}
	
	public LoginTimelineItemBuilder fromIp(String ipAddress) {
		((GenericActionTarget)actionObject).setSummary("Login from ip address: " + ipAddress);
		return this;
	}
	
	
	public LoginTimelineItemBuilder time(long actDate) {
		this.actionDate = Long.valueOf(actDate);
		return this;
	}

	private static final long serialVersionUID = 1L;

	public UserTimelineItem build() {
		UserTimelineItem uti = new UserTimelineItem(actorId, actionObject,
				 target, userActionType);
		
		uti.setDetail(detail);
		uti.setActionDate(actionDate);
		uti.setActionObject(actionObject);
		uti.setTarget(target);
		uti.setDetailActions(detailActions);
		uti.setUserActionType(userActionType);
		return uti;
	}

}
