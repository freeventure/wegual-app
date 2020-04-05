package com.wegual.userservice.es.actions;

import app.wegual.common.model.UserTimelineItem;

public class ESAsyncCommandRunner implements Runnable {
	
	private ESUserAction action;
	private UserTimelineItem data;
	
	public ESAsyncCommandRunner(ESUserAction action, UserTimelineItem uti) {
		this.action = action;
		data = uti;
	}
	
	public void run() {
		action.theAction(data);
	}
}