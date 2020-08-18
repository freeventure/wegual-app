package com.wegual.giveupservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.wegual.giveupservice.ElasticSearchConfig;

import app.wegual.common.model.GenericActionTarget;
import app.wegual.common.model.GiveUpTimelineItem;
import app.wegual.common.model.TimelineItemDetailActions;
import app.wegual.common.model.UserActionTargetType;
import app.wegual.common.model.UserActionType;
import app.wegual.common.util.ESIndices;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GiveUpTimelineService {

	@Autowired
	private ElasticSearchConfig esConfig;

	public List<GiveUpTimelineItem> getTimeline(String giveupId) {

		SearchRequest searchRequest = new SearchRequest(ESIndices.GIVEUP_TIMELINE_INDEX);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.termQuery("giveup_id", giveupId)).size(100)
		.sort(SortBuilders.fieldSort("date_stamp").order(SortOrder.DESC));
		searchRequest.source(sourceBuilder);

		try {
			RestHighLevelClient client = esConfig.getElasticsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			if(searchResponse.getHits().getTotalHits().value > 0L)
				return parseTimelineSearchHits(searchResponse);
			return new ArrayList<GiveUpTimelineItem>();
		} catch (IOException e) {
			log.error("Error getting timeline for giveup with id: " + giveupId , e);
			return new ArrayList<GiveUpTimelineItem>();
		}
	}

	@SuppressWarnings("unchecked")
	private List<GiveUpTimelineItem> parseTimelineSearchHits(SearchResponse searchResponse) {
		Map<String, Object> src = null;
		Map<String, Object> action = null;
		Map<String, Object> target = null;
		Map<String, Object> detailActions = null;
		List<GiveUpTimelineItem> timeline = new ArrayList<GiveUpTimelineItem>();
		GiveUpTimelineItem timelineItem = null;
		for (SearchHit hit: searchResponse.getHits()) {
			src = hit.getSourceAsMap();
			action = (Map<String, Object>)src.get("action_object");
			target = (Map<String, Object>)src.get("target_object");
			detailActions = (Map<String, Object>)src.get("detail_actions");
			log.info(src.toString());
			String actorId = src.get("actor_id").toString();
			Object detail = src.get("detail");
			UserActionType uat = UserActionType.valueOf(src.get("verb").toString());
			timelineItem = new GiveUpTimelineItem(actorId,
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
		log.info("Parsed timeline items, total items: " + timeline.size());
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
