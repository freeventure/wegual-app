package com.wegual.webapp.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.wegual.webapp.PrettyTimeUtil;

import app.wegual.common.model.ActionTarget;
import app.wegual.common.model.BeneficiaryActionTargetType;
import app.wegual.common.model.BeneficiaryActionType;
import app.wegual.common.model.TimelineItem;
import app.wegual.common.model.BeneficiaryTimelineItem;

public class BeneficiaryTimelineUIElement extends TimelineUIElement<String, BeneficiaryActionTargetType, BeneficiaryActionType> {

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
	protected void processSummary(TimelineItem<String, BeneficiaryActionTargetType, BeneficiaryActionType> timelineItem) {
		
		this.summary = timelineItem.getActionObject().getSummary();
		if(summary.contains("${target_name_link}")) {
			summary = summary.replace("${target_name_link}",
					buildTargetNameReference("${target_name_link}", timelineItem));
		}
	}
	
	private String buildTargetNameReference(String source, TimelineItem<String, BeneficiaryActionTargetType, BeneficiaryActionType> timelineItem) {
		
		String targetRef = "<a href=\"${link}\">${target}</a>";
		ActionTarget<String, BeneficiaryActionTargetType> actionTarget = timelineItem.getTarget();
		if(actionTarget != null && actionTarget.getName() != null && actionTarget.getPermalink() != null)
		{
			targetRef = targetRef.replace("${target}", actionTarget.getName());
			targetRef = targetRef.replace("${link}", actionTarget.getPermalink());
			return targetRef;
		}
		return source;
		
	}

	@Override
	protected void processDetail(TimelineItem<String, BeneficiaryActionTargetType, BeneficiaryActionType> timelineItem) {
		
		if(StringUtils.isEmpty(timelineItem.getDetail()))
			return;
		
		this.hasDetail = true;
		this.detail = timelineItem.getDetail();
	}

	@Override
	protected void processIconAndColor(TimelineItem<String, BeneficiaryActionTargetType, BeneficiaryActionType> timelineItem) {
		
		// beneficiary confirms pledge
		if(BeneficiaryActionType.CONFIRM_PLEDGE.equals(timelineItem.getActionType())) {
			
			if (timelineItem.getTarget() != null
					&& BeneficiaryActionTargetType.USER.equals(timelineItem.getTarget().getActionTargetType())) {
				this.iconColor = "bg-success";
				this.iconName = "fa-hand-holding-usd";
				return;

			}

		}
		
		// beneficiary login
		if(BeneficiaryActionType.LOGIN.equals(timelineItem.getActionType())) {
			
			if (timelineItem.getTarget() != null
					&& BeneficiaryActionTargetType.BENEFICIARY.equals(timelineItem.getTarget().getActionTargetType())) {
				this.iconColor = "bg-warning";
				this.iconName = "fa-chalkboard-teacher";
				return;
			}
		}
		
		// beneficiary update profile
		if(BeneficiaryActionType.UPDATE.equals(timelineItem.getActionType())) {
			
			if (timelineItem.getTarget() != null
					&& BeneficiaryActionTargetType.PROFILE.equals(timelineItem.getTarget().getActionTargetType())) {
				this.iconColor = "bg-purple";
				this.iconName = "fa-address-card";
				return;
			}
		}
		
		// account create
		if (BeneficiaryActionType.CREATE.equals(timelineItem.getActionType())) {

			if (timelineItem.getTarget() != null
					&& BeneficiaryActionTargetType.ACCOUNT.equals(timelineItem.getTarget().getActionTargetType())) {
				 
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
	protected void processDateStamp(TimelineItem<String, BeneficiaryActionTargetType, BeneficiaryActionType> timelineItem) {
		this.timeAgo = PrettyTimeUtil.prettyTime().format(
				new Date(timelineItem.getActionDate().longValue()));
		this.dateTime = timelineItem.getActionDate().longValue();
	}
	
	@Override
	protected void processDetailActions(TimelineItem<String, BeneficiaryActionTargetType, BeneficiaryActionType> timelineItem) {
		if(timelineItem.getDetailActions() != null) {
			this.showView = timelineItem.getDetailActions().isViewDetail();
			//this.showShare = timelineItem.getDetailActions().isShare();
		}
	}

}
