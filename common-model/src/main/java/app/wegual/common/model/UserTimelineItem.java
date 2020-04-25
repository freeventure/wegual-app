package app.wegual.common.model;

public class UserTimelineItem extends TimelineItem<String, UserActionTargetType, UserActionType> {

	public UserTimelineItem(String actorId, ActionTarget<String, UserActionTargetType> actionObject, ActionTarget<String, UserActionTargetType> actionTarget,
			UserActionType uat) {
		super(actorId, actionObject, actionTarget, uat);
	}

	public UserTimelineItem(String actorId) {
		super(actorId, null, null, null);
	}
	private static final long serialVersionUID = 1L;
}
