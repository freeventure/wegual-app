package com.wegual.pledgeservice.message;

import app.wegual.common.model.ActionTarget;
import app.wegual.common.model.FeedItem;
import app.wegual.common.model.FeedItemDetailActions;
import app.wegual.common.model.GenericActionTarget;
import app.wegual.common.model.GenericItem;
import app.wegual.common.model.Pledge;
import app.wegual.common.model.UserActionTargetType;
import app.wegual.common.model.UserActionType;
import app.wegual.common.model.PledgeFeedItem;

public class PledgeFeedItemBuilder extends PledgeFeedItem{

	public PledgeFeedItemBuilder(GenericItem<String> actor, String detail, FeedItemDetailActions detailActions, ActionTarget<String> actionObject) {
		super(actor, detail, detailActions, actionObject);
		// TODO Auto-generated constructor stub
	}
	
	public PledgeFeedItemBuilder() {
		super();
		actionObject = new GenericActionTarget(UserActionTargetType.PLEDGE, "", "", "", "");
		target = new GenericActionTarget(UserActionTargetType.BENEFICIARY, "", "", "", "");
	}

	public PledgeFeedItemBuilder feed(Pledge pledge) {
		this.actor = pledge.getPledgedBy();
		
		GenericActionTarget gat = (GenericActionTarget) actionObject;
		gat.setId(pledge.getId());
		gat.setName("Pledge");
		gat.setPermalink("/pledge/"+pledge.getId());
		gat.setSummary("${actor_name_link} took a pledge for ${target_name_link}");
		
		GenericActionTarget gt = (GenericActionTarget) target;
		gt.setId(pledge.getBeneficiary().getId());
		gt.setName(pledge.getBeneficiary().getName());
		gt.setPermalink(pledge.getBeneficiary().getPermalink());
		gt.setSummary("");
				
		this.actionDate = System.currentTimeMillis();
		this.userActionType = UserActionType.PLEDGE;
		
		this.detail = pledge.getDescription();
		
		this.detailActions = new FeedItemDetailActions(0, 0, 0);
		return this;

	}
	public PledgeFeedItem build() {
		
		PledgeFeedItem pfi = new PledgeFeedItem();
		pfi.setActionDate(actionDate);
		pfi.setActionObject(actionObject);
		pfi.setActor(actor);
		pfi.setDetail(detail);
		pfi.setDetailActions(detailActions);
		pfi.setTarget(target);
		pfi.setUserActionType(userActionType);
		return pfi;
	}
}
