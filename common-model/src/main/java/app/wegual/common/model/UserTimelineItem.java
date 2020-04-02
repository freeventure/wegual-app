package app.wegual.common.model;

public class UserTimelineItem extends TimelineItem<String> {

	public UserTimelineItem(String actorId, ActionTarget<String> actionObject, ActionTarget<String> actionTarget,
			UserActionType uat) {
		super(actorId, actionObject, actionTarget, uat);
	}

	public UserTimelineItem(String actorId) {
		super(actorId, null, null, null);
	}
	private static final long serialVersionUID = 1L;
}
