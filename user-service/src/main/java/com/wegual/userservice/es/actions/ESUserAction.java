package com.wegual.userservice.es.actions;

import app.wegual.common.model.UserTimelineItem;

@FunctionalInterface
public interface ESUserAction {
	void theAction(UserTimelineItem uti);
}
