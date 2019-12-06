package com.wegual.pledgeservice.analytics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCount;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.wegual.common.rest.model.BeneficiarySnapshot;

@Service
public class PledgeAnalyticsService {

	@Autowired
	private RestHighLevelClient client;
	
	// get Pledge count in the system
	public Long pledgeCount() {
		
		SearchRequest searchRequest = new SearchRequest("pledge_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.matchAllQuery()); 
		sourceBuilder.size(0); 		
		searchRequest.source(sourceBuilder);
		
		try {
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			return searchResponse.getHits().getTotalHits();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Long(0L);
		}
	}
	
	// get total counts for the beneficiary
	public BeneficiarySnapshot countsForBeneficiary(Long beneficiaryId) {
		
		BeneficiarySnapshot benSnap = new BeneficiarySnapshot().withBeneficiaryId(beneficiaryId);
		SearchRequest searchRequest = new SearchRequest("pledge_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.termQuery("beneficiary_id", beneficiaryId.toString()));
		
		// Add aggregations
		ValueCountAggregationBuilder aggregation = AggregationBuilders.count("pledge_count")
		        .field("pledge_id");
		sourceBuilder.aggregation(aggregation);
		
		aggregation = AggregationBuilders.count("giveup_count")
		        .field("giveup_id");
		sourceBuilder.aggregation(aggregation);

		aggregation = AggregationBuilders.count("user_count")
		        .field("user_id");
		sourceBuilder.aggregation(aggregation);
		
		sourceBuilder.size(0); 		
		searchRequest.source(sourceBuilder);
		
		try {
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			Aggregations aggregations = searchResponse.getAggregations();

			ValueCount count = aggregations.get("pledge_count");
			benSnap.setPledgesCount(count.getValue());
			
			count = aggregations.get("giveup_count");
			benSnap.setGiveUpCount(count.getValue());

			count = aggregations.get("user_count");
			benSnap.setUserCount(count.getValue());
			
			return benSnap;
		} catch (IOException e) {
			e.printStackTrace();
			return BeneficiarySnapshot.sample();
		}
	}
	
	// get top 10 beneficiaries by pledges
	public List<BeneficiarySnapshot> popularByPledgeCount() {
		
		List<BeneficiarySnapshot> top10 = new ArrayList<>();
		return top10;
	}
	
	
}
