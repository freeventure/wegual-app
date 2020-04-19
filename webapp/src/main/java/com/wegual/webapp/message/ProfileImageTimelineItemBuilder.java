package com.wegual.webapp.message;

import app.wegual.common.model.ActionTarget;
import app.wegual.common.model.GenericActionTarget;
import app.wegual.common.model.TimelineItem;
import app.wegual.common.model.UserActionTargetType;
import app.wegual.common.model.UserActionType;
import app.wegual.common.model.UserTimelineItem;

public class ProfileImageTimelineItemBuilder extends TimelineItem<String> {
	
	public ProfileImageTimelineItemBuilder(String actorId, ActionTarget<String> actionObject,
			ActionTarget<String> actionTarget, UserActionType uat) {
		super(actorId, actionObject, actionTarget, uat);
	}

	public ProfileImageTimelineItemBuilder() {
		super(null, null, null, UserActionType.UPDATE);
		detail = "";
		actionObject = new GenericActionTarget(UserActionTargetType.IMAGE, "", "", "", "");
		target = new GenericActionTarget(UserActionTargetType.PROFILE, "", "", "", "");
		
	}
	
	public ProfileImageTimelineItemBuilder userId(String userId) {
		GenericActionTarget gat = (GenericActionTarget)target;
		gat.setId(userId);
		gat.setPermalink("/users/" + userId);
		this.actorId = userId;
		
		return this;
	}
	
	public ProfileImageTimelineItemBuilder userName(String name) {
		GenericActionTarget gat = (GenericActionTarget)target;
		gat.setName(name);
		return this;
	}
	
	public ProfileImageTimelineItemBuilder udpateImage(String imageId) {
		GenericActionTarget gat = (GenericActionTarget)actionObject;
		gat.setId(imageId);
		gat.setPermalink("/users/public/profile/image/" + imageId);
		gat.setSummary("User updated profile picture");
		return this;
	}
	
	
	public ProfileImageTimelineItemBuilder time(long actDate) {
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
