//package com.wegual.webapp.message;
//
//import app.wegual.common.model.ActionTarget;
//import app.wegual.common.model.GenericActionTarget;
//import app.wegual.common.model.TimelineItem;
//import app.wegual.common.model.UserActionTargetType;
//import app.wegual.common.model.UserActionType;
//import app.wegual.common.model.UserTimelineItem;
//
//public abstract class LoginTimelineItemBuilder<T, ATT, AT> extends TimelineItem<T, ATT, AT> {
//	
//	public LoginTimelineItemBuilder(T actorId, ActionTarget<T, ATT > actionObject,
//			ActionTarget<T, ATT> actionTarget, AT at) {
//		super(actorId, actionObject, actionTarget, at);
//	}
//	
//	public LoginTimelineItemBuilder<T, ATT, AT> userId(T userId) {
//		GenericActionTarget<T, ATT> gat = (GenericActionTarget<T, ATT>)actionObject;
//		this.actorId = userId;
//		gat.setId(actorId);
//		gat.setPermalink("/users/" + actorId);
//		gat = (GenericActionTarget<T, ATT>)target;
//		gat.setId(actorId);
//		gat.setPermalink("/users/" + actorId);
//		return this;
//	}
//	
//	public LoginTimelineItemBuilder<T, ATT, AT> userName(String name) {
//		GenericActionTarget gat = (GenericActionTarget)actionObject;
//		gat.setName(name);
//
//		gat = (GenericActionTarget)target;
//		gat.setName(name);
//		return this;
//	}
//	
//	public LoginTimelineItemBuilder<T, ATT, AT> fromIp(String ipAddress) {
//		((GenericActionTarget)actionObject).setSummary("Login from ip address: " + ipAddress);
//		return this;
//	}
//	
//	
//	public LoginTimelineItemBuilder<T, ATT, AT> time(long actDate) {
//		this.actionDate = Long.valueOf(actDate);
//		return this;
//	}
//
//	private static final long serialVersionUID = 1L;
//
//	public UserTimelineItem build() {
//		UserTimelineItem uti = new UserTimelineItem(actorId, actionObject,
//				 target, actionType);
//		
//		uti.setDetail(detail);
//		uti.setActionDate(actionDate);
//		uti.setActionObject(actionObject);
//		uti.setTarget(target);
//		uti.setDetailActions(detailActions);
//		uti.setActionType(actionType);
//		return uti;
//	}
//
//}
