package com.wegual.giveupservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.lucene.search.join.ScoreMode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wegual.giveupservice.ElasticSearchConfig;

import app.wegual.common.model.GenericItem;
import app.wegual.common.model.GiveUp;
import app.wegual.common.model.GiveUpLike;
import app.wegual.common.model.Pledge;
import app.wegual.common.util.ESIndices;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GiveUpService {

	@Autowired
	private ElasticSearchConfig esConfig;
	
	public List<GiveUp> getAllGiveup(){
		SearchRequest searchRequest = new SearchRequest(ESIndices.GIVEUP_INDEX);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.matchAllQuery());
		searchRequest.source(sourceBuilder);
		
		RestHighLevelClient client = esConfig.getElasticsearchClient();
		
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

	@SuppressWarnings("unchecked")
	public List<GiveUp> suggestGiveUpToLike(String userId) {
		log.info("Searching for all giveup suggestion for :" + userId);
		List<GiveUp> giveups = new ArrayList<GiveUp>();

		RestHighLevelClient client = esConfig.getElasticsearchClient();

		SearchRequest searchRequest = new SearchRequest(ESIndices.GIVEUP_LIKE_INDEX);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.nestedQuery("user",QueryBuilders.boolQuery().must(QueryBuilders.termQuery("user.id", userId)), ScoreMode.None));
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse;
		
		SearchRequest giveupRequest = new SearchRequest(ESIndices.GIVEUP_INDEX);
		SearchSourceBuilder giveupBuilder = new SearchSourceBuilder();
		giveupBuilder.query(QueryBuilders.matchAllQuery());
		giveupRequest.source(giveupBuilder);
		SearchResponse giveupResponse;
		
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			giveupResponse = client.search(giveupRequest, RequestOptions.DEFAULT);
			for(SearchHit hit: giveupResponse.getHits()) {
				String idInGiveUp =((String)((Map<String, Object>)hit.getSourceAsMap()).get("giveup_id"));
				GiveUp giveupObject = new ObjectMapper().readValue(hit.getSourceAsString(), GiveUp.class);
				int flag = 0;
				Map<String, Object> srcforgiveuplike = null;
				Map<String, Object> giveupliked = null;
				for(SearchHit giveuplike: searchResponse.getHits()) {
					srcforgiveuplike = giveuplike.getSourceAsMap();
					giveupliked = (Map<String, Object>) srcforgiveuplike.get("giveup");
					String idInGiveUpLike = (String) giveupliked.get("id");
					if(idInGiveUpLike.equals(idInGiveUp)) {
						flag=1;
						break;
					}
				}
				if(flag==0) {
					giveups.add(giveupObject);
				}
			}
		} catch (IOException e) {
			log.info("Failed to fetch giveup", e);
		}
		return giveups;

	}
	
	public List<GenericItem<String>> allGiveUpLikedByUser(String userId){
		log.info("Searching for all giveups like by :" + userId);
		List<GenericItem<String>> giveups = new ArrayList<GenericItem<String>>();
		
		SearchRequest searchRequest = new SearchRequest(ESIndices.GIVEUP_LIKE_INDEX);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.nestedQuery("user",QueryBuilders.boolQuery().must(QueryBuilders.termQuery("user.id", userId)), ScoreMode.None));
		searchRequest.source(sourceBuilder);
		
		RestHighLevelClient client = esConfig.getElasticsearchClient();
		
		try {
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			for(SearchHit hit : searchResponse.getHits()) {
				GiveUpLike gul = new ObjectMapper().readValue(hit.getSourceAsString(), GiveUpLike.class);
				giveups.add(gul.getGiveup());
			}
		} catch (Exception e) {
			log.info("Failed to fetch giveups liked by user", e);
		}
		return giveups;
	}
	
	public List<GenericItem<String>> getAllGiveupUserPledgedFor(String userId) {
		log.info("Inside giveup service");
		Set<GenericItem<String>> giveups= new HashSet<GenericItem<String>>();
		List<GenericItem<String>> giveup = new ArrayList<GenericItem<String>>();
		try {
			SearchRequest searchRequest = new SearchRequest(ESIndices.PLEDGE_INDEX);
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
			sourceBuilder.query(QueryBuilders.nestedQuery("pledged_by",QueryBuilders.boolQuery().must(QueryBuilders.termQuery("pledged_by.id", userId)), ScoreMode.None));
			searchRequest.source(sourceBuilder);
			
			RestHighLevelClient client = esConfig.getElasticsearchClient();
			
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			for(SearchHit hit : searchResponse.getHits()) {
				Pledge pledge = new ObjectMapper().readValue(hit.getSourceAsString(), Pledge.class);
				giveups.add(pledge.getGiveUp());
			}
			for(GenericItem<String> g : giveups) {
				giveup.add(g);
			}
			return giveup;
		} catch (Exception e) {
			log.error("Error getting giveups user pledged for: " + userId , e);
		}
		return giveup;
	}

}
