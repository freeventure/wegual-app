package com.wegual.webapp.ui.model;

import app.wegual.common.model.TimelineItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class TimelineUIElement<T> {
	
	protected String iconName;
	protected String iconColor;
	
	protected String detail;
	protected boolean hasDetail;
	protected boolean showView;
	protected boolean showShare;
	protected String timeAgo;
	
	protected long dateTime;
	
	protected String summary;
	
	public void buildFrom(TimelineItem<T> timelineItem) {
		
		processSummary(timelineItem);
		processDetail(timelineItem);
		processDetailActions(timelineItem);
		processIconAndColor(timelineItem);
		processDateStamp(timelineItem);
	}

	abstract protected void processDetailActions(TimelineItem<T> timelineItem);
	abstract protected void processSummary(TimelineItem<T> timelineItem);
	abstract protected void processDetail(TimelineItem<T> timelineItem);
	abstract protected void processIconAndColor(TimelineItem<T> timelineItem);
	abstract protected void processDateStamp(TimelineItem<T> timelineItem);
}
