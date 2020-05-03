package com.wegual.beneficiaryservice.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
import app.wegual.common.rest.model.BeneficiarySnapshot;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BeneficiaryService {

	@Autowired
	private ElasticSearchConfig esConfig;
	
	public Beneficiary getBeneficiary(Long id) {
		SearchRequest searchRequest = new SearchRequest("beneficiary_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.termQuery("beneficiary_id", id)); 	
		searchRequest.source(sourceBuilder);
		Beneficiary beneficiary = null;
		try {
			RestHighLevelClient client = esConfig.getElastcsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			for(SearchHit searchHit : searchResponse.getHits().getHits()){
				beneficiary = new ObjectMapper().readValue(searchHit.getSourceAsString(), Beneficiary.class);
			}
			return beneficiary;

		} catch (IOException e) {
			log.error("Error getting beneficiary with id: " + id , e);
			e.printStackTrace();
			return beneficiary;
		}
	}
	public BeneficiarySnapshot getBeneficiarySnapshot(Long id) {
		return new BeneficiarySnapshot(id, 2L, 4L, 5L, 0.0, new HashMap<String, Double>());
	}
}