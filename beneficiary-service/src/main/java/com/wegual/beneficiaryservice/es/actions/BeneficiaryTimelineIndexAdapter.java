package com.wegual.beneficiaryservice.es.actions;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;

import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentBuilder;

import app.wegual.common.model.ActionTarget;
import app.wegual.common.model.BeneficiaryTimelineItem;
import app.wegual.common.model.GenericActionTarget;
import app.wegual.common.model.TimelineItemDetailActions;
import app.wegual.common.model.UserActionTargetType;
import app.wegual.common.model.UserActionType;

public class BeneficiaryTimelineIndexAdapter {
	
	public XContentBuilder indexJson(BeneficiaryTimelineItem bti) throws Exception {
		
		XContentBuilder xb= jsonBuilder()
		.startObject()
        .field("actor_id", bti.getActorId())
        .field("date_stamp", bti.getActionDate())
        .field("verb", bti.getUserActionType().toString())
        .field("detail", bti.getDetail());

		getActionTargetObject(xb, "action_object", bti.getActionObject());
		getActionTargetObject(xb, "target_object", bti.getTarget());
		if(bti.getDetailActions()!=null)
			getDetailActionObject(xb, "detail_actions", bti.getDetailActions());
        xb.endObject();

        return xb;
	}
	
	private XContentBuilder getDetailActionObject(XContentBuilder source, String objectName, TimelineItemDetailActions da) throws IOException {
		return source.startObject(objectName)
		        .field("view_detail", da.isViewDetail())
		        .field("share", da.isShare())
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
		
		BeneficiaryTimelineItem bti = new BeneficiaryTimelineItem("db061a6d-3020-4ba5-998d-c1268959b869",
				gat,
				gatTarget, UserActionType.PLEDGE);
		bti.setActionDate(1585666800000L);
		bti.setDetailActions(tida);
		bti.setDetail("You pledged $200 to OSAAT USA.");
		
		System.out.println(Strings.toString(new BeneficiaryTimelineIndexAdapter().indexJson(bti)));
	}
}
