package com.wegual.giveupservice.analytics;

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

import app.wegual.common.rest.model.GiveUpFollowers;


@Service
public class GiveUpAnalyticsService {

	@Autowired
	private RestHighLevelClient client;
	
	// get GiveUp count in the system
	public Long giveUpCount() {
		
		SearchRequest searchRequest = new SearchRequest("giveup_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.matchAllQuery()); 
		sourceBuilder.size(0); 		
		searchRequest.source(sourceBuilder);
		
		try {
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			return searchResponse.getHits().getTotalHits().value;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Long(0L);
		}
	}
	
	// get follower count for a beneficiary
	public GiveUpFollowers followersCount(Long giveUpId) {
		
		SearchRequest searchRequest = new SearchRequest("giveup_follow_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.termQuery("giveup", giveUpId.toString())); 
		sourceBuilder.size(0); 		
		searchRequest.source(sourceBuilder);
		
		try {
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			
			return new GiveUpFollowers(giveUpId, searchResponse.getHits().getTotalHits().value);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return GiveUpFollowers.sample();
		}
	}
	
	// get top 10 beneficiaries by follower count
	public List<GiveUpFollowers> popularGiveUps() {
		
		List<GiveUpFollowers> top10 = new ArrayList<>();
		
		// build request
		SearchRequest searchRequest = new SearchRequest("giveup_follow_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		
		// Add aggregation
		TermsAggregationBuilder aggregation = AggregationBuilders.terms("followers")
		        .field("giveup");
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
				top10.add(new GiveUpFollowers(Long.valueOf(bucket.getKeyAsString()),
						new Long(bucket.getDocCount())));
			}
			
			return top10;
		} catch (IOException e) {
			
			e.printStackTrace();
			return GiveUpFollowers.samplePopular();
		}
	}
	
	
}
