package com.wegual.pledgeservice.analytics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
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
		
		// User and give up must return unique counts
		// so use Cardinality aggregation
		CardinalityAggregationBuilder cab = AggregationBuilders.cardinality("giveup_count")
		        .field("giveup_id");
		sourceBuilder.aggregation(cab);

		cab = AggregationBuilders.cardinality("user_count")
		        .field("user_id");
		sourceBuilder.aggregation(cab);
		
		TermsAggregationBuilder tab =  AggregationBuilders.terms("total_pledged")
				.field("currency");
		SumAggregationBuilder sab = AggregationBuilders.sum("total_amount").field("amount");
		tab.subAggregation(sab);
		
		sourceBuilder.aggregation(tab);
		
		sourceBuilder.size(0); 		
		searchRequest.source(sourceBuilder);
		
		try {
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			Aggregations aggregations = searchResponse.getAggregations();

			ValueCount count = aggregations.get("pledge_count");
			benSnap.setPledgesCount(count.getValue());
			
			Cardinality uniqueCount = aggregations.get("giveup_count");
			benSnap.setGiveUpCount(uniqueCount.getValue());

			uniqueCount = aggregations.get("user_count");
			benSnap.setUserCount(count.getValue());
			
			Terms terms = aggregations.get("total_pledged");
			Map<String, Double> amountsByCurrency = new LinkedHashMap<>();
			Sum amount = null;
			for(Terms.Bucket bucket: terms.getBuckets()) {
				amount = bucket.getAggregations().get("total_amount");
				amountsByCurrency.put(bucket.getKeyAsString(), amount.getValue());
			}
			benSnap.setAmountByCurrency(amountsByCurrency);
			
			return benSnap;
		} catch (IOException e) {
			e.printStackTrace();
			return BeneficiarySnapshot.sample();
		}
	}
	
	// get top 10 beneficiaries by pledges
	public List<BeneficiarySnapshot> topTenBeneficiariesByPledgeCount() {
		
		BeneficiarySnapshot beSnap = null;
		List<BeneficiarySnapshot> top10 = new ArrayList<>();
		SearchRequest searchRequest = new SearchRequest("pledge_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 

		// Add aggregations
		// count of documents in the index by beneficiary_id is the number of
		// pledges made for the beneficiary
		TermsAggregationBuilder tab =  AggregationBuilders.terms("total_pledges")
				.field("beneficiary_id");
		sourceBuilder.aggregation(tab);
		sourceBuilder.size(0); 		
		searchRequest.source(sourceBuilder);
		
		try {
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			Aggregations aggregations = searchResponse.getAggregations();

			Terms terms = aggregations.get("total_pledges");
			for(Terms.Bucket bucket: terms.getBuckets()) {
				beSnap = new BeneficiarySnapshot().withBeneficiaryId(Long.valueOf(bucket.getKeyAsString()));
				
				beSnap.setPledgesCount(bucket.getDocCount());
				top10.add(beSnap);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return top10;
		}
		return top10;
	}
	
	// get top 10 beneficiaries by amounts pledged
	public List<BeneficiarySnapshot> topTenBeneficiariesByAmounts() {
		BeneficiarySnapshot beSnap = null;
		List<BeneficiarySnapshot> top10 = new ArrayList<>();
		SearchRequest searchRequest = new SearchRequest("pledge_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 

		// Add aggregations
		TermsAggregationBuilder tab =  AggregationBuilders.terms("total_pledged")
				.field("beneficiary_id")
				// order must be added to sort by amounts descending in the
				// sub-aggregation
				.order(BucketOrder.aggregation("total_amount", false))
				.subAggregation(
						AggregationBuilders.sum("total_amount").field("amount")
				);
		sourceBuilder.aggregation(tab);
		sourceBuilder.size(0); 		
		searchRequest.source(sourceBuilder);
		
		try {
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			Aggregations aggregations = searchResponse.getAggregations();

			Terms terms = aggregations.get("total_pledged");
			Sum amount = null;
			for(Terms.Bucket bucket: terms.getBuckets()) {
				beSnap = new BeneficiarySnapshot().withBeneficiaryId(Long.valueOf(bucket.getKeyAsString()));
				amount = bucket.getAggregations().get("total_amount");
				beSnap.setTotalPledged(amount.getValue());
				top10.add(beSnap);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return top10;
		}
		return top10;
	}
	
}
