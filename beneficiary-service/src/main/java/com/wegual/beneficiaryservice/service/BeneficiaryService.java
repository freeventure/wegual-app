package com.wegual.beneficiaryservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;

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
		log.info("Inside Beneficiary Service");
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
		
		BeneficiarySnapshot beneficiarySnapshot= new BeneficiarySnapshot();
		beneficiarySnapshot.setBeneficiaryId(id);
		beneficiarySnapshot.setUserCount(getFollowerCountForBeneficiary(id));
		beneficiarySnapshot.setGiveUpCount(0L);
		beneficiarySnapshot.setPledgesCount(getPledgeCountForBeneficiary(id));
		beneficiarySnapshot.setTotalPledged(0.0);
		beneficiarySnapshot.setAmountByCurrency(getAmountByCurrency(id));
		//getGiveUpCountForBeneficiary(id);
		return beneficiarySnapshot;
		//return new BeneficiarySnapshot(id, 2L, 4L, 5L, 0.0, new HashMap<String, Double>());
	}
	
	public Long getPledgeCountForBeneficiary(Long benId) {
		SearchRequest searchRequest = new SearchRequest("pledge_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.nestedQuery("beneficiary", QueryBuilders.boolQuery().must(QueryBuilders.termQuery("beneficiary.id", benId)), ScoreMode.None));
		sourceBuilder.size(0); 
		searchRequest.source(sourceBuilder);
		log.info("Inside Beneficiary Service");
		try {
			RestHighLevelClient client = esConfig.getElastcsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			return searchResponse.getHits().getTotalHits();
		} catch (IOException e) {
			log.error("Error getting pledge count for beneficiary with id: " + benId , e);
			return new Long(0L);
		}
	}
	
	public Long getFollowerCountForBeneficiary(Long benId) {
		
		SearchRequest searchRequest = new SearchRequest("beneficiary_follow_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.nestedQuery("beneficiary_followee", 
			QueryBuilders.boolQuery().must(QueryBuilders.termQuery("beneficiary_followee.id", benId)), ScoreMode.None)); 
		sourceBuilder.size(0); 		
		searchRequest.source(sourceBuilder);
		log.info("Inside Beneficiary Service");
		try {
			RestHighLevelClient client = esConfig.getElastcsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			return searchResponse.getHits().getTotalHits();
		} catch (IOException e) {
			log.error("Error getting followers count for beneficiary with id: " + benId , e);
			return new Long(0L);
		}
	}
	
//	public Long getGiveUpCountForBeneficiary(Long benId) {
//		
//		SearchRequest searchRequest = new SearchRequest("pledge_idx");
//		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
//		sourceBuilder.query(QueryBuilders.nestedQuery("beneficiary_followee", 
//			QueryBuilders.boolQuery().must(QueryBuilders.termQuery("beneficiary_followee.id", benId)), ScoreMode.None)); 
//		sourceBuilder.size(0); 		
//		searchRequest.source(sourceBuilder);
//		NestedAggregationBuilder aggregation = AggregationBuilders.nested("aggs", "give_up").subAggregation(AggregationBuilders.cardinality("giveups")
//				.field("give_up.id"));
//		sourceBuilder.aggregation(aggregation);
//		log.info("Inside Beneficiary Service");
//		try {
//			RestHighLevelClient client = esConfig.getElastcsearchClient();
//			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//			Aggregation aggs = searchResponse.getAggregations().get("aggs").;
//			Aggregation giveUps = aggs.getAggregations().get("giveups");
//			return ();
//		} catch (IOException e) {
//			log.error("Error getting followers count for beneficiary with id: " + benId , e);
//			return new Long(0L);
//		}
//	}
//	
	public HashMap<String, Double> getAmountByCurrency(Long benId){
		
		HashMap<String, Double> amountByCurrency = new HashMap<String, Double>();
		
		SearchRequest searchRequest = new SearchRequest("pledge_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.nestedQuery("beneficiary", QueryBuilders.boolQuery().must(QueryBuilders.termQuery("beneficiary.id", benId)), ScoreMode.None));
		
		AggregationBuilder aggregation = AggregationBuilders.terms("currency_type").field("currency")
		.subAggregation(AggregationBuilders.sum("sum").field("amount"));
		
		sourceBuilder.aggregation(aggregation);
		searchRequest.source(sourceBuilder);
		
		try {
			RestHighLevelClient client = esConfig.getElastcsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			Terms currencyType = searchResponse.getAggregations().get("currency_type");
			// For each entry
			for (Bucket bucket : currencyType.getBuckets()) {
				Sum sum = bucket.getAggregations().get("sum");
				amountByCurrency.put(bucket.getKeyAsString(), sum.getValue());
				System.out.println(bucket.getKeyAsString());      // Term
				System.out.println(sum.getValue());
			}
			return amountByCurrency;
		}
		catch(Exception e) {
			log.info("Error getting amount by currency for beneficiary with id: " + benId , e);
			return new HashMap<String, Double>();
		}
	}
	
}