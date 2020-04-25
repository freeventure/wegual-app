package com.wegual.beneficiaryservice.es.actions;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;

import app.wegual.common.model.BeneficiaryActionTarget;
import app.wegual.common.model.BeneficiaryTimelineItem;

public class BeneficiaryTimelineIndexAdapter {
	public XContentBuilder indexJson(BeneficiaryTimelineItem bti) throws Exception {
		
		XContentBuilder xb= jsonBuilder()
		.startObject()
        .field("actor_id", bti.getActorId())
        .field("date_stamp", bti.getActionDate())
        .field("verb", bti.getActionType().toString())
        .field("detail", bti.getDetail());

		getActionTargetObject(xb, "action_object", (BeneficiaryActionTarget) bti.getActionObject());
		getActionTargetObject(xb, "target_object", (BeneficiaryActionTarget) bti.getTarget());
        xb.endObject();

        return xb;
	}
	
	private XContentBuilder getActionTargetObject(XContentBuilder source, String objectName, BeneficiaryActionTarget bat) throws IOException {
		return source.startObject(objectName)
        .field("id", bat.getId())
        .field("name", bat.getName())
        .field("summary", bat.getSummary())
        .field("permalink", bat.getPermalink())
        .field("action_type", bat.getActionTargetType().toString())
        .endObject();
	}

}
