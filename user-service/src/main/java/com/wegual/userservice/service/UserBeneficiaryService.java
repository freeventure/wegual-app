package com.wegual.userservice.service;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wegual.userservice.ElasticSearchConfig;

import app.wegual.common.model.Beneficiary;
import app.wegual.common.model.BeneficiaryFollowItem;
import app.wegual.common.model.GenericItem;
import app.wegual.common.model.GiveUp;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserBeneficiaryService {
	
	@Autowired
	private ElasticSearchConfig esConfig;
	
	public  List<GenericItem> getBeneficiaryFollowees(String userId) {
		
		SearchRequest searchRequest = new SearchRequest("beneficiary_follow_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.nestedQuery("user_follower", QueryBuilders.boolQuery().must(QueryBuilders.termQuery("user_follower.id", userId)), ScoreMode.None))
		.size(10)
		.sort(SortBuilders.fieldSort("follow_date").order(SortOrder.DESC));
		searchRequest.source(sourceBuilder);
		log.info("Inside User Beneficiary Service");
		try {
			RestHighLevelClient client = esConfig.getElastcsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			if(searchResponse.getHits().getTotalHits().value > 0L)
				return parseBeneficiarySearchHits(searchResponse);
			return new ArrayList<GenericItem>();
		} catch (IOException e) {
			log.error("Error getting beneficiary followees for user: " + userId , e);
			return new ArrayList<GenericItem>();
		}
	}
	
	private List<GenericItem> parseBeneficiarySearchHits(SearchResponse searchResponse){
		
		List<GenericItem> benFolloweeList = new ArrayList<GenericItem>();
		BeneficiaryFollowItem benFolloweeItem;
		SearchHit[] searchHits = searchResponse.getHits().getHits();
		for (SearchHit searchHit : searchHits) {
			try {
				benFolloweeItem = new ObjectMapper().readValue(searchHit.getSourceAsString(), BeneficiaryFollowItem.class);
				benFolloweeList.add(benFolloweeItem.getBeneficiaryFollowee());
			}
			catch(Exception e) {
				log.info("Json Parsing Exception" + e);
				return new ArrayList<GenericItem>();
			}
		}
		
		return benFolloweeList;
	}
	
	public List<Beneficiary> getAllBeneficiary(){
		SearchRequest searchRequest = new SearchRequest("beneficiary_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.matchAllQuery());
		searchRequest.source(sourceBuilder);
		
		RestHighLevelClient client = esConfig.getElastcsearchClient();
		
		try {
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			List<Beneficiary> bens = new ArrayList<Beneficiary>();
			if(searchResponse.getHits().getTotalHits().value>0L) {
				for (SearchHit hit: searchResponse.getHits()) {
					Beneficiary beneficiary = new ObjectMapper().readValue(hit.getSourceAsString(), Beneficiary.class);
					bens.add(beneficiary);
				}
				
				return bens;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ArrayList<Beneficiary>();
	}

}
