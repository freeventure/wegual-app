package com.wegual.webapp.ui.model;

import app.wegual.common.model.FeedItem;
import app.wegual.common.model.TimelineItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class FeedUIElement<T> {

	protected String pictureLink;
	protected String detail;
	protected String timeAgo;
	protected long dateTime;
	protected String summary;


	public void buildFrom(FeedItem<T> feedItem, String userServiceUrl) {

		processSummary(feedItem);
		processDetail(feedItem);
		processDateStamp(feedItem);
		processPictureLink(feedItem, userServiceUrl);
	}
	
	abstract protected void processSummary(FeedItem<T> feedItem);
	abstract protected void processDetail(FeedItem<T> feedItem);
	abstract protected void processPictureLink(FeedItem<T> feedItem, String userServiceUrl);
	abstract protected void processDateStamp(FeedItem<T> feedItem);

}
