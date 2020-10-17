package com.wegual.userservice.es.actions;

import app.wegual.common.model.PledgeFeedItem;

@FunctionalInterface
public interface ESUserFeedAction {
	void theAction(PledgeFeedItem pfi);
}
