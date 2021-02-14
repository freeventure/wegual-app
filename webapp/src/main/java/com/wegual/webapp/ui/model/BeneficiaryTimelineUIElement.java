package com.wegual.webapp.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.wegual.webapp.PrettyTimeUtil;

import app.wegual.common.model.ActionTarget;
import app.wegual.common.model.BeneficiaryTimelineItem;
import app.wegual.common.model.TimelineItem;
import app.wegual.common.model.UserActionTargetType;
import app.wegual.common.model.UserActionType;

public class BeneficiaryTimelineUIElement extends TimelineUIElement<String>{
	
	public static List<BeneficiaryTimelineUIElement> build(List<BeneficiaryTimelineItem> items)
	{
		BeneficiaryTimelineUIElement uiElement = null;
		List<BeneficiaryTimelineUIElement> uiElements = new ArrayList<>();
		if(items != null)
			for(BeneficiaryTimelineItem uti : items) {
				uiElement = new BeneficiaryTimelineUIElement();
				uiElement.buildFrom(uti);
				uiElements.add(uiElement);
			}
		return uiElements;
	}
	
	public static Map<String, List<BeneficiaryTimelineUIElement>> groupByDate(List<BeneficiaryTimelineUIElement> elements) {
		Map<String, List<BeneficiaryTimelineUIElement>> groupedTimelineElements =
				new LinkedHashMap<>();
		List<BeneficiaryTimelineUIElement> newElements = null;
		
		String dateValue = null;
		for(BeneficiaryTimelineUIElement elem : elements) {
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
	protected void processSummary(TimelineItem<String> timelineItem) {
		
		this.summary = timelineItem.getTarget().getSummary();
		if(summary.contains("${target_name_link}")) {
			summary = summary.replace("${target_name_link}",
					buildTargetNameReference("${target_name_link}", timelineItem));
		}
	}
	
	private String buildTargetNameReference(String source, TimelineItem<String> timelineItem) {
		
		String targetRef = "<a href=\"${link}\">${target}</a>";
		ActionTarget<String> actionTarget = timelineItem.getTarget();
		if(actionTarget != null && actionTarget.getName() != null && actionTarget.getPermalink() != null)
		{
			targetRef = targetRef.replace("${target}", actionTarget.getName());
			targetRef = targetRef.replace("${link}", actionTarget.getPermalink());
			return targetRef;
		}
		return source;
		
	}
	
	@Override
	protected void processDetail(TimelineItem<String> timelineItem) {
		
		if(StringUtils.isEmpty(timelineItem.getDetail()))
			return;
		
		this.hasDetail = true;
		this.detail = timelineItem.getDetail();
	}
	
	@Override
	protected void processIconAndColor(TimelineItem<String> timelineItem) {
		
		// user pledge
		if(UserActionType.PLEDGE.equals(timelineItem.getUserActionType())) {
			
			if (timelineItem.getTarget() != null
					&& UserActionTargetType.BENEFICIARY.equals(timelineItem.getTarget().getActionType())) {
				this.iconColor = "bg-success";
				this.iconName = "fa-hand-holding-usd";
				return;

			}

		}

		// user follow
		if(UserActionType.FOLLOW_USER.equals(timelineItem.getUserActionType())) {
			
			if (timelineItem.getTarget() != null
					&& UserActionTargetType.USER.equals(timelineItem.getTarget().getActionType())) {
				this.iconColor = "bg-info";
				this.iconName = "fa-user-friends";
				return;
			}
		}
		
		// user login
		if(UserActionType.LOGIN.equals(timelineItem.getUserActionType())) {
			
			if (timelineItem.getTarget() != null
					&& UserActionTargetType.USER.equals(timelineItem.getTarget().getActionType())) {
				this.iconColor = "bg-warning";
				this.iconName = "fa-chalkboard-teacher";
				return;
			}
		}
		
		// user update profile
		if(UserActionType.UPDATE.equals(timelineItem.getUserActionType())) {
			
			if (timelineItem.getTarget() != null
					&& UserActionTargetType.PROFILE.equals(timelineItem.getTarget().getActionType())) {
				this.iconColor = "bg-purple";
				this.iconName = "fa-address-card";
				return;
			}
		}
		
		// account create
		if (UserActionType.CREATE.equals(timelineItem.getUserActionType())) {

			if (timelineItem.getTarget() != null
					&& UserActionTargetType.ACCOUNT.equals(timelineItem.getTarget().getActionType())) {
				 
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
	protected void processDateStamp(TimelineItem<String> timelineItem) {
		this.timeAgo = PrettyTimeUtil.prettyTime().format(
				new Date(timelineItem.getActionDate().longValue()));
		this.dateTime = timelineItem.getActionDate().longValue();
	}

	@Override
	protected void processDetailActions(TimelineItem<String> timelineItem) {
		if(timelineItem.getDetailActions() != null) {
			this.showView = timelineItem.getDetailActions().isViewDetail();
			this.showShare = timelineItem.getDetailActions().isShare();
		}
	}
}