package app.wegual.common.model;

public class BeneficiaryTimelineItem extends TimelineItem<String>{
	
	private static final long serialVersionUID = 4358328207894508179L;

	public BeneficiaryTimelineItem() {
		super();
	}
	
	public BeneficiaryTimelineItem(String actorId) {
		super(actorId, null, null, null);
	}
	
	public BeneficiaryTimelineItem(String actorId, ActionTarget<String> actionObject, ActionTarget<String> actionTarget, UserActionType uat) {
		super(actorId, actionObject, actionTarget, uat);
	}
}
