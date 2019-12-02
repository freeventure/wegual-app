package com.wegual.beneficiaryservice.analytics;

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
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.wegual.common.rest.model.BeneficiaryFollowers;

@Service
public class BeneficaryAnalyticsService {

	@Autowired
	private RestHighLevelClient client;
	
	// get follower count for a beneficiary
	public BeneficiaryFollowers followersCount(Long beneficiaryId) {
		
		SearchRequest searchRequest = new SearchRequest("beneficiary_follow_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.termQuery("beneficiary", beneficiaryId.toString())); 
		sourceBuilder.size(0); 		
		searchRequest.source(sourceBuilder);
		
		try {
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			
			return new BeneficiaryFollowers(beneficiaryId, searchResponse.getHits().getTotalHits());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return BeneficiaryFollowers.sample();
		}
	}
	
	// get top 10 beneficiaries by follower count
	public List<BeneficiaryFollowers> popularBeneficaries() {
		
		List<BeneficiaryFollowers> top10 = new ArrayList<>();
		
		// build request
		SearchRequest searchRequest = new SearchRequest("beneficiary_follow_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		
		// Add aggregation
		TermsAggregationBuilder aggregation = AggregationBuilders.terms("followers")
		        .field("beneficiary");
		sourceBuilder.aggregation(aggregation);
		
		sourceBuilder.size(0); 		
		searchRequest.source(sourceBuilder);

		// execute search
		try {
			
			// process returned aggregation
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			Aggregations aggregations = searchResponse.getAggregations();
			Terms byFollowersAggregation = aggregations.get("followers");
			
			// user streams here?
			for(Bucket bucket: byFollowersAggregation.getBuckets()) {
				top10.add(new BeneficiaryFollowers(Long.valueOf(bucket.getKeyAsString()),
						new Long(bucket.getDocCount())));
			}
			
			return top10;
		} catch (IOException e) {
			
			e.printStackTrace();
			return BeneficiaryFollowers.samplePopular();
		}
	}
	
	
}
