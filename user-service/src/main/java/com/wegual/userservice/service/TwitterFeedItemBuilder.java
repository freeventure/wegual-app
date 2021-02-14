package com.wegual.userservice.service;

import java.util.Map;

import com.wegual.userservice.UserUtils;

import app.wegual.common.model.ActionTarget;
import app.wegual.common.model.FeedItemDetailActions;
import app.wegual.common.model.GenericActionTarget;
import app.wegual.common.model.GenericItem;
import app.wegual.common.model.TwitterFeedItem;
import app.wegual.common.model.UserActionTargetType;
import app.wegual.common.model.UserActionType;

public class TwitterFeedItemBuilder extends TwitterFeedItem{

	private static final long serialVersionUID = -6520306914349875587L;

	public TwitterFeedItemBuilder(GenericItem<String> actor, String detail, FeedItemDetailActions detailActions, ActionTarget<String> actionObject) {
		super(actor, detail, detailActions, actionObject);
		// TODO Auto-generated constructor stub
	}

	public TwitterFeedItemBuilder() {
		super();
		actionObject = new GenericActionTarget(UserActionTargetType.TWITTER, "", "", "", "");
		target = new GenericActionTarget(UserActionTargetType.USER, "", "", "", "");
	}

	public TwitterFeedItemBuilder feed(Map<String, Object> tweet, String userId) {
		this.actor = UserUtils.getUserGenericItem(userId);

		GenericActionTarget gat = (GenericActionTarget) actionObject;
		gat.setId((String) tweet.get("id_str"));
		gat.setName("Tweet");
		gat.setPermalink("/tweet/" + tweet.get("id_str"));
		gat.setSummary("${actor_name_link} tweeted this on twitter");

		GenericActionTarget gt = (GenericActionTarget) target;
		gt.setId(this.actor.getId());
		gt.setName(this.actor.getName());
		gt.setPermalink(this.actor.getPermalink());
		gt.setSummary("Tweet fetched from twitter");

		this.actionDate = (Long) tweet.get("created_at");
		this.userActionType = UserActionType.TWITTER_TWEET;

		this.detail = (String) tweet.get("text");

		this.detailActions = new FeedItemDetailActions(0, 0, 0);
		return this;

	}

	public TwitterFeedItem build() {

		TwitterFeedItem tfi = new TwitterFeedItem();
		tfi.setActionDate(actionDate);
		tfi.setActionObject(actionObject);
		tfi.setActor(actor);
		tfi.setDetail(detail);
		tfi.setDetailActions(detailActions);
		tfi.setTarget(target);
		tfi.setUserActionType(userActionType);
		return tfi;
	}
}
