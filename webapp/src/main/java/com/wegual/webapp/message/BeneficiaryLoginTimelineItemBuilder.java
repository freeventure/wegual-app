package com.wegual.webapp.message;

import app.wegual.common.model.ActionTarget;
import app.wegual.common.model.BeneficiaryTimelineItem;
import app.wegual.common.model.BeneficiaryActionTarget;
import app.wegual.common.model.BeneficiaryActionTargetType;
import app.wegual.common.model.BeneficiaryActionType;

public class BeneficiaryLoginTimelineItemBuilder extends BeneficiaryTimelineItem{
	
	private static final long serialVersionUID = 1L;

	public BeneficiaryLoginTimelineItemBuilder(String actorId, ActionTarget<String, BeneficiaryActionTargetType > actionObject,
			ActionTarget<String, BeneficiaryActionTargetType> actionTarget, BeneficiaryActionType uat) {
		super(actorId, actionObject, actionTarget, uat);
	}
	
	public BeneficiaryLoginTimelineItemBuilder() {
		super(null, null, null, BeneficiaryActionType.LOGIN);
		detail = "";
		actionObject = new BeneficiaryActionTarget(BeneficiaryActionTargetType.LOGIN, "", "", "", "");
		target = new BeneficiaryActionTarget(BeneficiaryActionTargetType.BENEFICIARY, "", "", "", "");
		
	}
	
	public BeneficiaryLoginTimelineItemBuilder benId(String benId) {
		BeneficiaryActionTarget bat = (BeneficiaryActionTarget)actionObject;
		this.actorId = benId;
		bat.setId(actorId);
		bat.setPermalink("/beneficiary/" + actorId);
		
		bat = (BeneficiaryActionTarget)target;
		bat.setId(actorId);
		bat.setPermalink("/beneficiary/" + actorId);
		return this;
	}
	
	public BeneficiaryLoginTimelineItemBuilder beneficiaryName(String name) {
		BeneficiaryActionTarget bat = (BeneficiaryActionTarget)actionObject;
		bat.setName(name);

		bat = (BeneficiaryActionTarget)target;
		bat.setName(name);
		return this;
	}
	
	public BeneficiaryLoginTimelineItemBuilder fromIp(String ipAddress) {
		((BeneficiaryActionTarget)actionObject).setSummary("Login from ip address: " + ipAddress);
		return this;
	}
	
	
	public BeneficiaryLoginTimelineItemBuilder time(long actDate) {
		this.actionDate = Long.valueOf(actDate);
		return this;
	}


	public BeneficiaryTimelineItem build() {
		BeneficiaryTimelineItem bti = new BeneficiaryTimelineItem(actorId, actionObject,
				 target, actionType);
		
		bti.setDetail(detail);
		bti.setActionDate(actionDate);
		bti.setActionObject(actionObject);
		bti.setTarget(target);
		bti.setDetailActions(detailActions);
		bti.setActionType(actionType);
		return bti;
	}

}
