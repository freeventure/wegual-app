package com.wegual.beneficiaryservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wegual.beneficiaryservice.ElasticSearchConfig;

import app.wegual.common.model.BeneficiaryTimelineItem;
import app.wegual.common.model.GenericActionTarget;
import app.wegual.common.model.TimelineItemDetailActions;
import app.wegual.common.model.UserActionTargetType;
import app.wegual.common.model.UserActionType;
import app.wegual.common.util.ESIndices;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BeneficiaryTimelineService {

	@Autowired
	private ElasticSearchConfig esConfig;
	
	public List<BeneficiaryTimelineItem> getTimeline(String benId) {
		
		SearchRequest searchRequest = new SearchRequest(ESIndices.BENEFICIARY_TIMELINE_INDEX);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.nestedQuery("target_object", QueryBuilders.boolQuery().must(QueryBuilders.termQuery("target_object.id", benId)), ScoreMode.None)).size(100)
		.sort(SortBuilders.fieldSort("date_stamp").order(SortOrder.DESC));
		searchRequest.source(sourceBuilder);
		
		try {
			RestHighLevelClient client = esConfig.getElasticsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			if(searchResponse.getHits().getTotalHits().value > 0L)
				return parseTimelineSearchHits(searchResponse);
			return new ArrayList<BeneficiaryTimelineItem>();
		} catch (IOException e) {
			log.error("Error getting follower count for user: " + benId , e);
			return new ArrayList<BeneficiaryTimelineItem>();
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<BeneficiaryTimelineItem> parseTimelineSearchHits(SearchResponse searchResponse) {
		Map<String, Object> src = null;
		Map<String, Object> action = null;
		Map<String, Object> target = null;
		Map<String, Object> detailActions = null;
		List<BeneficiaryTimelineItem> timeline = new ArrayList<BeneficiaryTimelineItem>();
		BeneficiaryTimelineItem timelineItem = null;
		for (SearchHit hit: searchResponse.getHits()) {
			src = hit.getSourceAsMap();
			action = (Map<String, Object>)src.get("action_object");
			target = (Map<String, Object>)src.get("target_object");
			detailActions = (Map<String, Object>)src.get("detail_actions");
			log.info(src.toString());
			String actorId = src.get("actor_id").toString();
			Object detail = src.get("detail");
			UserActionType uat = UserActionType.valueOf(src.get("verb").toString());
			timelineItem = new BeneficiaryTimelineItem(actorId,
					parseActionTarget(action), parseActionTarget(target), uat);
			if(detail != null)
				timelineItem.setDetail(detail.toString());
			Object actionDate = src.get("date_stamp");
			if(actionDate != null && !actionDate.toString().isEmpty())
				timelineItem.setActionDate(Long.valueOf(actionDate.toString()));

			TimelineItemDetailActions tda = parseDetailActions(detailActions);
			if(tda != null)
				timelineItem.setDetailActions(tda);
			timeline.add(timelineItem);
		}
		log.info("Parsed beneficiary timeline items, total items: " + timeline.size());
		return timeline;
	}
	
	private TimelineItemDetailActions parseDetailActions(Map<String, Object> values) {
		TimelineItemDetailActions tda = new TimelineItemDetailActions();
		if(values == null)
			return null;
		Object object = values.get("view_detail");
		if(object!= null)
			tda.setViewDetail(Boolean.parseBoolean(object.toString()));
		object = values.get("share");
		if(object!= null)
			tda.setShare(Boolean.parseBoolean(object.toString()));
		return tda;
	}
	
	private GenericActionTarget parseActionTarget(Map<String, Object> values) {
		GenericActionTarget gat = new GenericActionTarget();
		Object object = values.get("id");
		if(object!= null)
			gat.setId(object.toString());
		object = values.get("name");
		if(object!= null)
			gat.setName(object.toString());
		object = values.get("summary");
		if(object!= null)
			gat.setSummary(object.toString());
		object = values.get("permalink");
		if(object!= null)
			gat.setPermalink(object.toString());
		object = values.get("action_type");
		if(object!= null)
			gat.setActionType(UserActionTargetType.valueOf(object.toString()));
		
		return gat;
	}
	
}