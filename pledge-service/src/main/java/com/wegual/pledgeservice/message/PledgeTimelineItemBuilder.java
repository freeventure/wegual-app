package com.wegual.pledgeservice.message;

import app.wegual.common.model.ActionTarget;
import app.wegual.common.model.BeneficiaryTimelineItem;
import app.wegual.common.model.GenericActionTarget;
import app.wegual.common.model.Pledge;
import app.wegual.common.model.TimelineItem;
import app.wegual.common.model.TimelineItemDetailActions;
import app.wegual.common.model.UserActionTargetType;
import app.wegual.common.model.UserActionType;
import app.wegual.common.model.UserTimelineItem;

public class PledgeTimelineItemBuilder extends TimelineItem<String>{

	public PledgeTimelineItemBuilder(String actorId, ActionTarget<String> actionObject, ActionTarget<String> actionTarget,
			UserActionType uat) {
		super(actorId, actionObject, actionTarget, uat);
	}
	public PledgeTimelineItemBuilder() {
		super(null, null, null, UserActionType.PLEDGE);
		actionObject = new GenericActionTarget(UserActionTargetType.PLEDGE, "", "", "", "");
		target = new GenericActionTarget(UserActionTargetType.BENEFICIARY, "", "", "", "");
		detailActions = new TimelineItemDetailActions(true, true);
	}
	public PledgeTimelineItemBuilder pledge(Pledge pledge) {
		GenericActionTarget gat = (GenericActionTarget) actionObject;
		gat.setId(pledge.getId());
		gat.setName("Pledge");
		gat.setPermalink("/pledge/"+pledge.getId());
		gat.setSummary("You made a pledge to ${target_name_link}");

		this.actorId = pledge.getPledgedBy().getId();

		GenericActionTarget gt = (GenericActionTarget) target;
		gt.setId(pledge.getBeneficiary().getId());
		gt.setName(pledge.getBeneficiary().getName());
		gt.setPermalink(pledge.getBeneficiary().getPermalink());
		gt.setSummary("");

		this.actionDate = System.currentTimeMillis();	
		
		this.detail = "Currency :" +pledge.getCurrency()+"\n"+"Amount :"+pledge.getAmount();
		return this;
	}
	
	public UserTimelineItem build() {
		UserTimelineItem uti = new UserTimelineItem(actorId, actionObject, target, userActionType);

		uti.setActorId(actorId);
		uti.setDetail(detail);
		uti.setActionDate(actionDate);
		uti.setActionObject(actionObject);
		uti.setTarget(target);
		uti.setDetailActions(detailActions);
		uti.setUserActionType(userActionType);
		return uti;
	}
	
	public BeneficiaryTimelineItem buildForBeneficiary() {
		BeneficiaryTimelineItem bti = new BeneficiaryTimelineItem(actorId, actionObject, target, userActionType);

		bti.setDetail(detail);
		bti.setActionDate(actionDate);
		bti.setActionObject(actionObject);
		bti.setTarget(target);
		bti.setDetailActions(detailActions);
		bti.setUserActionType(userActionType);
		return bti;
	}
}
