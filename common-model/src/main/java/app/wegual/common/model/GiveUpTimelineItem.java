package app.wegual.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GiveUpTimelineItem extends TimelineItem<String>{

	private static final long serialVersionUID = 1L;
	
	private String giveupId;

	public GiveUpTimelineItem() {
		super();
	}
	
	public GiveUpTimelineItem(String actorId) {
		super(actorId, null, null, null);
	}
	
	public GiveUpTimelineItem(String actorId, ActionTarget<String> actionObject, ActionTarget<String> actionTarget, UserActionType uat) {
		super(actorId, actionObject, actionTarget, uat);
	}
}