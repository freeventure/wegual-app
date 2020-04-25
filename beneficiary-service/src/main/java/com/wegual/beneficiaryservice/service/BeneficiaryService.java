package com.wegual.beneficiaryservice.service;

import java.io.IOException;

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
import com.wegual.beneficiaryservice.ElasticSearchConfig;

import app.wegual.common.model.Beneficiary;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BeneficiaryService {

	@Autowired
	private ElasticSearchConfig esConfig;
	
	public Beneficiary getBeneficiary(String username) {
		SearchRequest searchRequest = new SearchRequest("beneficiary_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.termQuery("username", username)); 	
		searchRequest.source(sourceBuilder);
		Beneficiary ben=null;
		try {
			RestHighLevelClient client = esConfig.getElastcsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			for(SearchHit searchHit : searchResponse.getHits().getHits()){
				Beneficiary beneficiary = new ObjectMapper().readValue(searchHit.getSourceAsString(),Beneficiary.class);
				ben = beneficiary;
			}
			return ben;

		} catch (IOException e) {
			log.error("Error getting beneficiary with username: " + username , e);
			e.printStackTrace();
			return ben;
		}
	}
}
