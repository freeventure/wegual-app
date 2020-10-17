package com.wegual.userservice.es.actions;

import app.wegual.common.model.PledgeFeedItem;

public class ESAsyncCommandRunnerForFeed implements Runnable{

	private ESUserFeedAction action;
	private PledgeFeedItem data;
	
	public ESAsyncCommandRunnerForFeed(ESUserFeedAction action, PledgeFeedItem pfi) {
		super();
		this.action = action;
		data = pfi;
	}
	
	@Override
	public void run() {
		action.theAction(data);
	}
	
	

}
