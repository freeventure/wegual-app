package com.wegual.pledgeservice.service;

import java.util.HashSet;
import java.util.Map;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wegual.pledgeservice.ElasticSearchConfig;

import app.wegual.common.model.PledgeAnalyticsForUser;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PledgeAnalyticService {
		
	@Autowired
	private ElasticSearchConfig esConfig;
	
		public PledgeAnalyticsForUser getCounts(String userId) {
		log.info("Inside pledge analytics service");
		try {
			SearchRequest searchRequest = new SearchRequest("pledge_idx");
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
			sourceBuilder.query(QueryBuilders.nestedQuery("pledged_by",QueryBuilders.boolQuery().must(QueryBuilders.termQuery("pledged_by.id", userId)), ScoreMode.None));
			searchRequest.source(sourceBuilder);
		
			RestHighLevelClient client = esConfig.getElastcsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			if(searchResponse.getHits().getTotalHits().value > 0L) {
				log.info("Got pledge document");
				return parsePledgeSearchHits(searchResponse, searchResponse.getHits().getTotalHits().value);
			}
			return new PledgeAnalyticsForUser();
		} catch (Exception e) {
			log.error("Error getting pledge count for user: " + userId , e);
			return new PledgeAnalyticsForUser();
		}
	}
		@SuppressWarnings("unchecked")
		private PledgeAnalyticsForUser parsePledgeSearchHits(SearchResponse searchResponse, long pledgecount) {
			Map<String, Object> src = null;
			Map<String, Object> beneficiary = null;
			Map<String, Object> giveup = null;
			HashSet<String> beneficiaryset = new HashSet<String>();
			HashSet<String> giveupset = new HashSet<String>();
			for (SearchHit hit: searchResponse.getHits()) {
				src = hit.getSourceAsMap();
				beneficiary = (Map<String, Object>)src.get("beneficiary");
				giveup = (Map<String, Object>)src.get("give_up");
				log.info(src.toString());
				String benstr = (String)beneficiary.get("id");
				String giveupstr = (String)giveup.get("id");

				beneficiaryset.add(benstr);
				giveupset.add(giveupstr);
			}
			PledgeAnalyticsForUser counts =new PledgeAnalyticsForUser(pledgecount, beneficiaryset.size(), giveupset.size());
			return counts;
		}
}
