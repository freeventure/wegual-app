package com.wegual.giveupservice.message;



import app.wegual.common.model.ActionTarget;
import app.wegual.common.model.GenericActionTarget;
import app.wegual.common.model.GenericItem;
import app.wegual.common.model.TimelineItem;
import app.wegual.common.model.UserActionTargetType;
import app.wegual.common.model.UserActionType;
import app.wegual.common.model.UserTimelineItem;

public class GiveUpLikeTimelineItemBuilder extends TimelineItem<String>{

	private static final long serialVersionUID = 1L;

	public GiveUpLikeTimelineItemBuilder(String actorId, ActionTarget<String> actionObject,	ActionTarget<String> actionTarget, 
			UserActionType uat) {
		super(actorId, actionObject, actionTarget, uat);
	}
	
	public GiveUpLikeTimelineItemBuilder(UserActionType uat) {
		super(null, null, null, uat);
		detail = "";
		actionObject = new GenericActionTarget(UserActionTargetType.USER, "", "", "", "");
		target = new GenericActionTarget(UserActionTargetType.GIVEUP, "", "", "", "");
	}
	
	public GiveUpLikeTimelineItemBuilder user(GenericItem<String> user) {
		GenericActionTarget gat = (GenericActionTarget)actionObject;
		gat.setId(user.getId());
		gat.setPermalink(user.getPermalink());
		gat.setName(user.getName());
		
		if(this.userActionType.equals(UserActionType.LIKE))
			gat.setSummary("User liked a GiveUp");
		else if(this.userActionType.equals(UserActionType.UNLIKE))
			gat.setSummary("User Unliked a GiveUp");
		this.actorId = user.getId();
		
		return this;
	}
	
	public GiveUpLikeTimelineItemBuilder giveup(GenericItem<String> giveup) {
		GenericActionTarget gat = (GenericActionTarget)target;
		gat.setId(giveup.getId());
		gat.setPermalink(giveup.getPermalink());
		gat.setName(giveup.getName());
		
		return this;
	}
	
	public GiveUpLikeTimelineItemBuilder time(long actDate) {
		this.actionDate = Long.valueOf(actDate);
		return this;
	}
	
	public UserTimelineItem build() {
		UserTimelineItem uti = new UserTimelineItem(actorId, actionObject, target, userActionType);
		
		uti.setDetail(detail);
		uti.setActionDate(actionDate);
		uti.setActionObject(actionObject);
		uti.setTarget(target);
		uti.setDetailActions(detailActions);
		uti.setUserActionType(userActionType);
		return uti;
	}
	
}
