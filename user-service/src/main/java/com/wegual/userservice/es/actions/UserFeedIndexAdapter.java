package com.wegual.userservice.es.actions;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;

import app.wegual.common.model.ActionTarget;
import app.wegual.common.model.FeedItemDetailActions;
import app.wegual.common.model.GenericItem;
import app.wegual.common.model.PledgeFeedItem;
import app.wegual.common.model.TwitterFeedItem;


public class UserFeedIndexAdapter {
	public XContentBuilder indexJson(Object obj) throws Exception {
		if(obj instanceof PledgeFeedItem) {
			PledgeFeedItem ufi = (PledgeFeedItem) obj;
			XContentBuilder xb= jsonBuilder()
				.startObject()
				.field("date_stamp", ufi.getActionDate())
				.field("detail", ufi.getDetail())
				.field("verb", ufi.getUserActionType());

				getActionTargetObject(xb, "action_object", ufi.getActionObject());
				getActionTargetObject(xb, "target_object", ufi.getTarget());
				getGenericItemObject(xb, "actor", ufi.getActor());
				if(ufi.getDetailActions()!=null)
					getDetailActionObject(xb, "detail_actions", ufi.getDetailActions());
				xb.endObject();

				return xb;
		}
		else if(obj instanceof TwitterFeedItem) {
			TwitterFeedItem tfi = (TwitterFeedItem) obj;
			XContentBuilder xb= jsonBuilder()
					.startObject()
					.field("date_stamp", tfi.getActionDate())
					.field("detail", tfi.getDetail())
					.field("verb", tfi.getUserActionType());

					getActionTargetObject(xb, "action_object", tfi.getActionObject());
					getActionTargetObject(xb, "target_object", tfi.getTarget());
					getGenericItemObject(xb, "actor", tfi.getActor());
					if(tfi.getDetailActions()!=null)
						getDetailActionObject(xb, "detail_actions", tfi.getDetailActions());
					xb.endObject();

					return xb;
		}
		return null;
	}
	
	
	private XContentBuilder getDetailActionObject(XContentBuilder source, String objectName, FeedItemDetailActions da) throws IOException {
		return source.startObject(objectName)
		        .field("likes", da.getLikes())
		        .field("shares", da.getShares())
		        .field("comment", da.getComment())
		        .endObject();
	}
	
	private XContentBuilder getGenericItemObject(XContentBuilder source, String objectName, GenericItem<String> at) throws IOException {
		return source.startObject(objectName)
		.field("id", at.getId())
		.field("name", at.getName())
		.field("permalink", at.getPermalink())
		.field("picture_link", at.getPictureLink())
		.endObject();	
	}
	
	
	private XContentBuilder getActionTargetObject(XContentBuilder source, String objectName, ActionTarget<String> gat) throws IOException {
		return source.startObject(objectName)
        .field("id", gat.getId())
        .field("name", gat.getName())
        .field("summary", gat.getSummary())
        .field("permalink", gat.getPermalink())
        .field("action_type", gat.getActionType().toString())
        .endObject();
	}

}
