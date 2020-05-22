package com.wegual.beneficiaryservice.message;

import app.wegual.common.model.ActionTarget;
import app.wegual.common.model.GenericActionTarget;
import app.wegual.common.model.GenericItem;
import app.wegual.common.model.TimelineItem;
import app.wegual.common.model.UserActionTargetType;
import app.wegual.common.model.UserActionType;
import app.wegual.common.model.UserTimelineItem;

public class BeneficiaryFollowTimelineItemBuilder extends TimelineItem<String>{

	private static final long serialVersionUID = 1L;

	public BeneficiaryFollowTimelineItemBuilder(String actorId, ActionTarget<String> actionObject,	ActionTarget<String> actionTarget, 
			UserActionType uat) {
		super(actorId, actionObject, actionTarget, uat);
	}
	
	public BeneficiaryFollowTimelineItemBuilder(UserActionType uat) {
		super(null, null, null, uat);
		detail = "";
		actionObject = new GenericActionTarget(UserActionTargetType.USER, "", "", "", "");
		target = new GenericActionTarget(UserActionTargetType.BENEFICIARY, "", "", "", "");
	}
	
	public BeneficiaryFollowTimelineItemBuilder user(GenericItem<String> user) {
		GenericActionTarget gat = (GenericActionTarget)actionObject;
		gat.setId(user.getId());
		gat.setPermalink(user.getPermalink());
		gat.setName(user.getName());
		
		if(this.userActionType.equals(UserActionType.FOLLOW_BENEFICIARY))
			gat.setSummary("User followed a beneficiary");
		else if(this.userActionType.equals(UserActionType.UNFOLLOW_BENEFICIARY))
			gat.setSummary("User unfollowed a beneficiary");
		this.actorId = user.getId();
		
		return this;
	}
	
	public BeneficiaryFollowTimelineItemBuilder beneficiary(GenericItem<String> ben) {
		GenericActionTarget gat = (GenericActionTarget)target;
		gat.setId(ben.getId());
		gat.setPermalink(ben.getPermalink());
		gat.setName(ben.getName());
		
		return this;
	}
	
	public BeneficiaryFollowTimelineItemBuilder time(long actDate) {
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
