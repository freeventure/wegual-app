package com.wegual.userservice.es.actions;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;

import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentBuilder;

import app.wegual.common.model.ActionTarget;
import app.wegual.common.model.GenericActionTarget;
import app.wegual.common.model.TimelineItemDetailActions;
import app.wegual.common.model.UserActionTargetType;
import app.wegual.common.model.UserActionType;
import app.wegual.common.model.UserTimelineItem;

public class UserTimelineIndexAdapter {
	public XContentBuilder indexJson(UserTimelineItem uti) throws Exception {
		
		XContentBuilder xb= jsonBuilder()
		.startObject()
        .field("actor_id", uti.getActorId())
        .field("date_stamp", uti.getActionDate())
        .field("verb", uti.getUserActionType().toString())
        .field("detail", uti.getDetail());

		getActionTargetObject(xb, "action_object", uti.getActionObject());
		getActionTargetObject(xb, "target_object", uti.getTarget());
        xb.endObject();

        return xb;
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
	
	public static void main(String[] args) throws Exception {
		
		TimelineItemDetailActions tida = new TimelineItemDetailActions();
		tida.setShare(true);
		tida.setViewDetail(true);
		
		GenericActionTarget gat = new GenericActionTarget();
		gat.setId("pledgeid");
		gat.setSummary("You made a pledge to ${target_name_link}.");
		gat.setName("Pledge");
		gat.setPermalink("/pledges/pledgeid");
		gat.setActionType(UserActionTargetType.PLEDGE);

		GenericActionTarget gatTarget = new GenericActionTarget();
		gatTarget.setId("beneficiaryid");
		gatTarget.setSummary("");
		gatTarget.setName("OSAAT USA");
		gatTarget.setPermalink("/beneficiaries/beneficiaryid");
		gatTarget.setActionType(UserActionTargetType.BENEFICIARY);
		
		UserTimelineItem uti = new UserTimelineItem("db061a6d-3020-4ba5-998d-c1268959b869",
				gat,
				gatTarget, UserActionType.PLEDGE);
		uti.setActionDate(1585666800000L);
		uti.setDetailActions(tida);
		uti.setDetail("You pledged $200 to OSAAT USA.");
		
		System.out.println(Strings.toString(new UserTimelineIndexAdapter().indexJson(uti)));
	}
}
