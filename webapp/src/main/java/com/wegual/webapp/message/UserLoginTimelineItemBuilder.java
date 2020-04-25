package com.wegual.webapp.message;

import app.wegual.common.model.ActionTarget;
import app.wegual.common.model.UserActionTarget;
import app.wegual.common.model.UserActionTargetType;
import app.wegual.common.model.UserActionType;
import app.wegual.common.model.UserTimelineItem;

public class UserLoginTimelineItemBuilder extends UserTimelineItem {
	
	public UserLoginTimelineItemBuilder(String actorId, ActionTarget<String, UserActionTargetType > actionObject,
			ActionTarget<String, UserActionTargetType> actionTarget, UserActionType uat) {
		super(actorId, actionObject, actionTarget, uat);
	}

	public UserLoginTimelineItemBuilder() {
		super(null, null, null, UserActionType.LOGIN);
		detail = "";
		actionObject = new UserActionTarget(UserActionTargetType.LOGIN, "", "", "", "");
		target = new UserActionTarget(UserActionTargetType.USER, "", "", "", "");
		
	}
	
	public UserLoginTimelineItemBuilder userId(String userId) {
		UserActionTarget uat = (UserActionTarget)actionObject;
		this.actorId = userId;
		uat.setId(actorId);
		uat.setPermalink("/users/" + actorId);
		
		uat = (UserActionTarget)target;
		uat.setId(actorId);
		uat.setPermalink("/users/" + actorId);
		return this;
	}
	
	public UserLoginTimelineItemBuilder userName(String name) {
		UserActionTarget uat = (UserActionTarget)actionObject;
		uat.setName(name);

		uat = (UserActionTarget)target;
		uat.setName(name);
		return this;
	}
	
	public UserLoginTimelineItemBuilder fromIp(String ipAddress) {
		((UserActionTarget)actionObject).setSummary("Login from ip address: " + ipAddress);
		return this;
	}
	
	
	public UserLoginTimelineItemBuilder time(long actDate) {
		this.actionDate = Long.valueOf(actDate);
		return this;
	}

	private static final long serialVersionUID = 1L;

	public UserTimelineItem build() {
		UserTimelineItem uti = new UserTimelineItem(actorId, actionObject,
				 target, actionType);
		
		uti.setDetail(detail);
		uti.setActionDate(actionDate);
		uti.setActionObject(actionObject);
		uti.setTarget(target);
		uti.setDetailActions(detailActions);
		uti.setActionType(actionType);
		return uti;
	}

}
