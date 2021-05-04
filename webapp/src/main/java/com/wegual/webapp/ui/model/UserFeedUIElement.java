package com.wegual.webapp.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.wegual.webapp.PrettyTimeUtil;

import app.wegual.common.model.ActionTarget;
import app.wegual.common.model.FeedItem;
import app.wegual.common.model.GenericItem;
import app.wegual.common.model.PledgeFeedItem;
import app.wegual.common.model.TimelineItem;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserFeedUIElement extends FeedUIElement<String>{

	@Autowired
	private EurekaClient discoveryClient;
	
	public static List<UserFeedUIElement> build(List<PledgeFeedItem> items, String userServiceUrl, List<String> likedFeedId){
		UserFeedUIElement uiElement = null;
		List<UserFeedUIElement> uiElements = new ArrayList<UserFeedUIElement>();
		if(items != null) {
			for(PledgeFeedItem pfi : items ) {
				uiElement = new UserFeedUIElement();
				System.out.println(pfi.getId());
				uiElement.setFeedId(pfi.getId());
				if(likedFeedId.contains(pfi.getId())) {
					uiElement.setLiked(true);
				}
				else {
					uiElement.setLiked(false);
				}
				uiElement.setDetailActions(pfi.getDetailActions());
				uiElement.buildFrom(pfi, userServiceUrl);
				uiElements.add(uiElement);
			}
		}
		return uiElements;
	}

//	public static Map<String, List<UserFeedUIElement>> groupByDate(List<UserFeedUIElement> elements) {
//		Map<String, List<UserFeedUIElement>> groupedTimelineElements =
//				new LinkedHashMap<>();
//		List<UserFeedUIElement> newElements = null;
//
//		String dateValue = null;
//		for(UserFeedUIElement elem : elements) {
//			dateValue = PrettyTimeUtil.prettyDayFormat(elem.dateTime);
//			if(groupedTimelineElements.containsKey(dateValue)) {
//				groupedTimelineElements.get(dateValue).add(elem);
//			}
//			else
//			{
//				newElements = new ArrayList<>();
//				newElements.add(elem);
//				groupedTimelineElements.put(dateValue, newElements);
//			}
//		}
//		return groupedTimelineElements;
//	}

	protected void processSummary(FeedItem<String> feedItem) {
		this.summary = feedItem.getActionObject().getSummary();
		if(summary.contains("${actor_name_link}")) {
			summary = summary.replace("${actor_name_link}",
					buildUserNameReference("${actor_name_link}", feedItem));
		}
		if(summary.contains("${target_name_link}")) {
			summary = summary.replace("${target_name_link}",
					buildTargetNameReference("${target_name_link}", feedItem));
		}
	}

	private String buildUserNameReference(String source, FeedItem<String> feedItem) {

		String targetRef = "<a href=\"${link}\">${actor}</a>";
		GenericItem<String> actor = feedItem.getActor();
		if(actor != null && actor.getName() != null && actor.getPermalink() != null)
		{
			targetRef = targetRef.replace("${actor}", actor.getName());
			targetRef = targetRef.replace("${link}", actor.getPermalink());
			return targetRef;
		}
		return source;

	}
	private String buildTargetNameReference(String source, FeedItem<String> feedItem) {
		
		String targetRef = "<a href=\"${link}\">${target}</a>";
		ActionTarget<String> actionTarget = feedItem.getTarget();
		if(actionTarget != null && actionTarget.getName() != null && actionTarget.getPermalink() != null)
		{
			targetRef = targetRef.replace("${target}", actionTarget.getName());
			targetRef = targetRef.replace("${link}", actionTarget.getPermalink());
			return targetRef;
		}
		return source;
		
	}
	
	@Override
	protected void processDetail(FeedItem<String> feedItem) {
		
		if(StringUtils.isEmpty(feedItem.getDetail()))
			return;
		
		this.detail = feedItem.getDetail();
	}
	
	@Override
	protected void processDateStamp(FeedItem<String> feedItem) {
		this.timeAgo = PrettyTimeUtil.prettyTime().format(
				new Date(feedItem.getActionDate().longValue()));
		this.dateTime = feedItem.getActionDate().longValue();
	}
	
	@Override
	protected void processPictureLink(FeedItem<String> feedItem, String userServiceUrl) {
//		if(StringUtils.isEmpty(feedItem.getActor().getPictureLink()))
//			this.pictureLink = ("/img/avatar-empty.png");
//		else
//		{
//			String userServiceUrl = StringUtils.removeEnd(EurekaServiceUtil.getEurekaServiceUtilObj().getUserServiceUrl(), "/");
//			log.info("User service URL is: " + userServiceUrl);
//			this.pictureLink = userServiceUrl + feedItem.getActor().getPictureLink();
//		}
		this.pictureLink = userServiceUrl + feedItem.getActor().getPictureLink();
	}

}
