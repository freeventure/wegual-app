package com.wegual.userservice.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wegual.userservice.ElasticSearchConfig;
import com.wegual.userservice.UserUtils;

import app.wegual.common.model.Pledge;
import app.wegual.common.model.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PledgeService {
	
	@Autowired
	private ElasticSearchConfig esConfig;
	
	public List<Map<String, Object>> getAllPledgeForUser(String userId){
		log.info("Inside pledge service");
		List<Map<String, Object>> pledges = new ArrayList<>();
		try {
			SearchRequest searchRequest = new SearchRequest("pledge_idx");
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
			sourceBuilder.query(QueryBuilders.nestedQuery("pledged_by",QueryBuilders.boolQuery().must(QueryBuilders.termQuery("pledged_by.id", userId)), ScoreMode.None));
			searchRequest.source(sourceBuilder);
			
			RestHighLevelClient client = esConfig.getElastcsearchClient();
			
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			if(searchResponse.getHits().getTotalHits().value>0L) {
				Map<String, Object> src = null;
				for (SearchHit hit: searchResponse.getHits()) {
					src = hit.getSourceAsMap();
					pledges.add(src);
				}
			}
			return pledges;
		} catch (Exception e) {
			log.error("Error getting pledges user: " + userId , e);
		}
		return pledges;
	}

}
