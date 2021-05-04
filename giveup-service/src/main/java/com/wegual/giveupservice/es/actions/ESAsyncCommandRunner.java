package com.wegual.giveupservice.es.actions;


import app.wegual.common.model.GiveUpTimelineItem;

public class ESAsyncCommandRunner implements Runnable{
	
	private ESGiveUpAction action;
	private GiveUpTimelineItem data;
	
	public ESAsyncCommandRunner(ESGiveUpAction action, GiveUpTimelineItem gti) {
		this.action = action;
		data = gti;
	}
	
	public void run() {
		action.theAction(data);
	}

}
