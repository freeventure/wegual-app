package com.wegual.webapp.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.wegual.webapp.PrettyTimeUtil;

import app.wegual.common.model.ActionTarget;
import app.wegual.common.model.TimelineItem;
import app.wegual.common.model.UserActionTargetType;
import app.wegual.common.model.UserActionType;
import app.wegual.common.model.UserTimelineItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserTimelineUIElement extends TimelineUIElement<String, UserActionTargetType, UserActionType> {
	
	
	public static List<UserTimelineUIElement> build(List<UserTimelineItem> items)
	{
		UserTimelineUIElement uiElement = null;
		List<UserTimelineUIElement> uiElements = new ArrayList<>();
		if(items != null)
			for(UserTimelineItem uti : items) {
				uiElement = new UserTimelineUIElement();
				uiElement.buildFrom(uti);
				uiElements.add(uiElement);
			}
		return uiElements;
	}
	
	public static Map<String, List<UserTimelineUIElement>> groupByDate(List<UserTimelineUIElement> elements) {
		Map<String, List<UserTimelineUIElement>> groupedTimelineElements =
				new LinkedHashMap<>();
		List<UserTimelineUIElement> newElements = null;
		
		String dateValue = null;
		for(UserTimelineUIElement elem : elements) {
			dateValue = PrettyTimeUtil.prettyDayFormat(elem.dateTime);
			if(groupedTimelineElements.containsKey(dateValue)) {
				groupedTimelineElements.get(dateValue).add(elem);
			}
			else
			{
				newElements = new ArrayList<>();
				newElements.add(elem);
				groupedTimelineElements.put(dateValue, newElements);
			}
		}
		return groupedTimelineElements;
	}
	
	@Override
	protected void processSummary(TimelineItem<String, UserActionTargetType, UserActionType> timelineItem) {
		
		this.summary = timelineItem.getActionObject().getSummary();
		if(summary.contains("${target_name_link}")) {
			summary = summary.replace("${target_name_link}",
					buildTargetNameReference("${target_name_link}", timelineItem));
		}
	}
	
	private String buildTargetNameReference(String source, TimelineItem<String, UserActionTargetType, UserActionType> timelineItem) {
		
		String targetRef = "<a href=\"${link}\">${target}</a>";
		ActionTarget<String, UserActionTargetType> actionTarget = timelineItem.getTarget();
		if(actionTarget != null && actionTarget.getName() != null && actionTarget.getPermalink() != null)
		{
			targetRef = targetRef.replace("${target}", actionTarget.getName());
			targetRef = targetRef.replace("${link}", actionTarget.getPermalink());
			return targetRef;
		}
		return source;
		
	}
	
	@Override
	protected void processDetail(TimelineItem<String, UserActionTargetType, UserActionType> timelineItem) {
		
		if(StringUtils.isEmpty(timelineItem.getDetail()))
			return;
		
		this.hasDetail = true;
		this.detail = timelineItem.getDetail();
	}
	
	@Override
	protected void processIconAndColor(TimelineItem<String, UserActionTargetType, UserActionType> timelineItem) {
		
		// user pledge
		if(UserActionType.PLEDGE.equals(timelineItem.getActionType())) {
			
			if (timelineItem.getTarget() != null
					&& UserActionTargetType.BENEFICIARY.equals(timelineItem.getTarget().getActionTargetType())) {
				this.iconColor = "bg-success";
				this.iconName = "fa-hand-holding-usd";
				return;

			}

		}

		// user follow
		if(UserActionType.FOLLOW.equals(timelineItem.getActionType())) {
			
			if (timelineItem.getTarget() != null
					&& UserActionTargetType.USER.equals(timelineItem.getTarget().getActionTargetType())) {
				this.iconColor = "bg-info";
				this.iconName = "fa-user-friends";
				return;
			}
		}
		
		// user login
		if(UserActionType.LOGIN.equals(timelineItem.getActionType())) {
			
			if (timelineItem.getTarget() != null
					&& UserActionTargetType.USER.equals(timelineItem.getTarget().getActionTargetType())) {
				this.iconColor = "bg-warning";
				this.iconName = "fa-chalkboard-teacher";
				return;
			}
		}
		
		// user update profile
		if(UserActionType.UPDATE.equals(timelineItem.getActionType())) {
			
			if (timelineItem.getTarget() != null
					&& UserActionTargetType.PROFILE.equals(timelineItem.getTarget().getActionTargetType())) {
				this.iconColor = "bg-purple";
				this.iconName = "fa-address-card";
				return;
			}
		}
		
		// account create
		if (UserActionType.CREATE.equals(timelineItem.getActionType())) {

			if (timelineItem.getTarget() != null
					&& UserActionTargetType.ACCOUNT.equals(timelineItem.getTarget().getActionTargetType())) {
				 
				this.iconColor = "bg-purple";
				this.iconName = "fa-user-check";
				return;
			}
		}
		// default color and icon
		this.iconColor = "bg-primary";
		this.iconName = "fa-user";
	}
	
	@Override
	protected void processDateStamp(TimelineItem<String, UserActionTargetType, UserActionType> timelineItem) {
		this.timeAgo = PrettyTimeUtil.prettyTime().format(
				new Date(timelineItem.getActionDate().longValue()));
		this.dateTime = timelineItem.getActionDate().longValue();
	}

	@Override
	protected void processDetailActions(TimelineItem<String, UserActionTargetType, UserActionType> timelineItem) {
		if(timelineItem.getDetailActions() != null) {
			this.showView = timelineItem.getDetailActions().isViewDetail();
			this.showShare = timelineItem.getDetailActions().isShare();
		}
	}
	
}
