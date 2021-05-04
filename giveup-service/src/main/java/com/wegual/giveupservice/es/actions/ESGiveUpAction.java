package com.wegual.giveupservice.es.actions;

import app.wegual.common.model.GiveUpTimelineItem;

@FunctionalInterface
public interface ESGiveUpAction {

	void theAction(GiveUpTimelineItem gti);
	
}
