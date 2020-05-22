package com.wegual.userservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import app.wegual.common.model.GenericItem;
import app.wegual.common.model.GiveUp;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class GiveupService {
	
	@Autowired
	private ElasticSearchConfig esConfig;
	
	
	public List<Object> getAllGiveupUserPledgedFor(String userId) {
		log.info("Inside giveup service");
		Set<Object> giveups= new HashSet<Object>();
		List<Object> giveup = new ArrayList<Object>();
		try {
			SearchRequest searchRequest = new SearchRequest("pledge_idx");
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
			sourceBuilder.query(QueryBuilders.nestedQuery("pledged_by",QueryBuilders.boolQuery().must(QueryBuilders.termQuery("pledged_by.id", userId)), ScoreMode.None));
			searchRequest.source(sourceBuilder);
			
			RestHighLevelClient client = esConfig.getElastcsearchClient();
			
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			System.out.println(searchResponse.getHits().getTotalHits().value);
			if(searchResponse.getHits().getTotalHits().value>0L) {
				Map<String, Object> src = null;
				Object g = null;
				for (SearchHit hit: searchResponse.getHits()) {
					src = hit.getSourceAsMap();
					g = src.get("give_up");
					giveups.add(g);
				}
				for(Object x : giveups) {
					giveup.add(x);
				}
				return giveup;
			}
		} catch (Exception e) {
			log.error("Error getting gveups user pledged for: " + userId , e);
		}
		return giveup;
	}
	
	public List<GiveUp> getAllGiveup(){
		SearchRequest searchRequest = new SearchRequest("giveup_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.matchAllQuery());
		searchRequest.source(sourceBuilder);
		
		RestHighLevelClient client = esConfig.getElastcsearchClient();
		
		try {
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			List<GiveUp> giveups = new ArrayList<GiveUp>();
			if(searchResponse.getHits().getTotalHits().value>0L) {
				for (SearchHit hit: searchResponse.getHits()) {
					GiveUp giveup = new ObjectMapper().readValue(hit.getSourceAsString(), GiveUp.class);
					giveups.add(giveup);
				}
				
				return giveups;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<GiveUp>();
	}
}