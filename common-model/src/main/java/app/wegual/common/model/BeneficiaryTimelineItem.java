package app.wegual.common.model;

public class BeneficiaryTimelineItem extends TimelineItem<String, BeneficiaryActionTargetType, BeneficiaryActionType>{
	
	public BeneficiaryTimelineItem(String actorId, ActionTarget<String, BeneficiaryActionTargetType> actionObject, ActionTarget<String, BeneficiaryActionTargetType> actionTarget,
			BeneficiaryActionType bat) {
		super(actorId, actionObject, actionTarget, bat);
	}
	
	public BeneficiaryTimelineItem(String actorId) {
		super(actorId, null, null, null);
	}
	private static final long serialVersionUID = 1L;

}
