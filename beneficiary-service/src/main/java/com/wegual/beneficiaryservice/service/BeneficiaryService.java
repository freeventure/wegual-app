package com.wegual.beneficiaryservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.Sum;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wegual.beneficiaryservice.ElasticSearchConfig;

import app.wegual.common.model.Beneficiary;
import app.wegual.common.model.BeneficiaryFollowItem;
import app.wegual.common.model.GenericItem;
import app.wegual.common.model.Pledge;
import app.wegual.common.rest.model.BeneficiarySnapshot;
import app.wegual.common.util.ESIndices;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BeneficiaryService {

	@Autowired
	private ElasticSearchConfig esConfig;
	
	public List<Beneficiary> getAllBeneficiary(){
		SearchRequest searchRequest = new SearchRequest(ESIndices.BENEFICIARY_INDEX);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.matchAllQuery());
		searchRequest.source(sourceBuilder);
		
		RestHighLevelClient client = esConfig.getElasticsearchClient();
		
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
	
	public Beneficiary getBeneficiary(String id) {
		SearchRequest searchRequest = new SearchRequest("beneficiary_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.termQuery("beneficiary_id", id)); 	
		searchRequest.source(sourceBuilder);
		log.info("Inside Beneficiary Service");
		Beneficiary beneficiary = null;
		try {
			RestHighLevelClient client = esConfig.getElasticsearchClient();
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
	public BeneficiarySnapshot getBeneficiarySnapshot(String id) {
		
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
	
	public Long getPledgeCountForBeneficiary(String benId) {
		SearchRequest searchRequest = new SearchRequest("pledge_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.nestedQuery("beneficiary", QueryBuilders.boolQuery().must(QueryBuilders.termQuery("beneficiary.id", benId)), ScoreMode.None));
		sourceBuilder.size(0); 
		searchRequest.source(sourceBuilder);
		log.info("Inside Beneficiary Service");
		try {
			RestHighLevelClient client = esConfig.getElasticsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			return searchResponse.getHits().getTotalHits().value;
		} catch (IOException e) {
			log.error("Error getting pledge count for beneficiary with id: " + benId , e);
			return (0L);
		}
	}
	
	public Long getFollowerCountForBeneficiary(String benId) {
		
		SearchRequest searchRequest = new SearchRequest("beneficiary_follow_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.nestedQuery("beneficiary_followee", 
			QueryBuilders.boolQuery().must(QueryBuilders.termQuery("beneficiary_followee.id", benId)), ScoreMode.None)); 
		sourceBuilder.size(0); 		
		searchRequest.source(sourceBuilder);
		log.info("Inside Beneficiary Service");
		try {
			RestHighLevelClient client = esConfig.getElasticsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			return searchResponse.getHits().getTotalHits().value;
		} catch (IOException e) {
			log.error("Error getting followers count for beneficiary with id: " + benId , e);
			return (0L);
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
	public HashMap<String, Double> getAmountByCurrency(String benId){
		
		HashMap<String, Double> amountByCurrency = new HashMap<String, Double>();
		
		SearchRequest searchRequest = new SearchRequest("pledge_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.nestedQuery("beneficiary", QueryBuilders.boolQuery().must(QueryBuilders.termQuery("beneficiary.id", benId)), ScoreMode.None));
		
		AggregationBuilder aggregation = AggregationBuilders.terms("currency_type").field("currency")
		.subAggregation(AggregationBuilders.sum("sum").field("amount"));
		
		sourceBuilder.aggregation(aggregation);
		searchRequest.source(sourceBuilder);
		
		try {
			RestHighLevelClient client = esConfig.getElasticsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			Terms currencyType = searchResponse.getAggregations().get("currency_type");
			// For each entry
			for (Bucket bucket : currencyType.getBuckets()) {
				Sum sum = (Sum) bucket.getAggregations().asMap().get("sum");
				amountByCurrency.put(bucket.getKeyAsString(), sum.getValue());
				// System.out.println(bucket.getKeyAsString());      // Term
//				System.out.println(sum.getValue());
			}
			return amountByCurrency;
		}
		catch(Exception e) {
			log.info("Error getting amount by currency for beneficiary with id: " + benId , e);
			return new HashMap<String, Double>();
		}
	}
	
	public List<Beneficiary> suggestBeneficiaryToFollow(String userId) {
		log.info("Searching for all beneficiary follow suggestion for :" + userId);
		List<Beneficiary> ben = new ArrayList<Beneficiary>();

		RestHighLevelClient client = esConfig.getElasticsearchClient();

		SearchRequest searchRequest = new SearchRequest(ESIndices.BENEFICIARY_FOLLOW_INDEX);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.nestedQuery("user_follower",QueryBuilders.boolQuery().must(QueryBuilders.termQuery("user_follower.id", userId)), ScoreMode.None));
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse;
		
		SearchRequest giveupRequest = new SearchRequest(ESIndices.BENEFICIARY_INDEX);
		SearchSourceBuilder benBuilder = new SearchSourceBuilder();
		benBuilder.query(QueryBuilders.matchAllQuery());
		giveupRequest.source(benBuilder);
		SearchResponse benResponse;
		
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			benResponse = client.search(giveupRequest, RequestOptions.DEFAULT);
			for(SearchHit hit: benResponse.getHits()) {
				String idInBen =((String)((Map<String, Object>)hit.getSourceAsMap()).get("beneficiary_id"));
				Beneficiary benObject = new ObjectMapper().readValue(hit.getSourceAsString(), Beneficiary.class);
				int flag = 0;
				Map<String, Object> srcforbenlike = null;
				Map<String, Object> benliked = null;
				for(SearchHit giveuplike: searchResponse.getHits()) {
					srcforbenlike = giveuplike.getSourceAsMap();
					benliked = (Map<String, Object>) srcforbenlike.get("beneficiary_followee");
					String idInBenLike = (String) benliked.get("id");
					if(idInBenLike.equals(idInBen)) {
						flag=1;
						break;
					}
				}
				if(flag==0) {
					ben.add(benObject);
				}
			}
		} catch (IOException e) {
			log.info("Failed to fetch giveup", e);
		}
		return ben;

	}
	
	public List<GenericItem<String>> allBeneficiaryFollowedByUser(String userId){
		log.info("Searching for all beneficiaries followed by :" + userId);
		List<GenericItem<String>> ben = new ArrayList<GenericItem<String>>();
		
		SearchRequest searchRequest = new SearchRequest(ESIndices.BENEFICIARY_FOLLOW_INDEX);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.nestedQuery("user_follower",QueryBuilders.boolQuery().must(QueryBuilders.termQuery("user_follower.id", userId)), ScoreMode.None));
		searchRequest.source(sourceBuilder);
		
		RestHighLevelClient client = esConfig.getElasticsearchClient();
		
		try {
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			for(SearchHit hit : searchResponse.getHits()) {
				BeneficiaryFollowItem bfi = new ObjectMapper().readValue(hit.getSourceAsString(), BeneficiaryFollowItem.class);
				ben.add(bfi.getBeneficiaryFollowee());
			}
		} catch (Exception e) {
			log.info("Failed to fetch beneficiaries followed by user", e);
		}
		return ben;
	}
	
	public List<GenericItem<String>> getAllBeneficiaryUserPledgedFor(String userId) {
		log.info("Inside ben service");
		Map<String, GenericItem<String>> bens = new HashMap<String, GenericItem<String>>();
		List<GenericItem<String>> ben = new ArrayList<GenericItem<String>>();
		try {
			SearchRequest searchRequest = new SearchRequest(ESIndices.PLEDGE_INDEX);
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
			sourceBuilder.query(QueryBuilders.nestedQuery("pledged_by",QueryBuilders.boolQuery().must(QueryBuilders.termQuery("pledged_by.id", userId)), ScoreMode.None));
			searchRequest.source(sourceBuilder);
			
			RestHighLevelClient client = esConfig.getElasticsearchClient();
			
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			for(SearchHit hit : searchResponse.getHits()) {
				Pledge pledge = new ObjectMapper().readValue(hit.getSourceAsString(), Pledge.class);
				bens.put(pledge.getBeneficiary().getId(), pledge.getBeneficiary());
			}
			Set<Map.Entry<String, GenericItem<String>>> st = bens.entrySet();
			for(Map.Entry<String, GenericItem<String>> mp : st) {
				ben.add(mp.getValue());
			}
			return ben;
		} catch (Exception e) {
			log.error("Error getting giveups user pledged for: " + userId , e);
		}
		return ben;
	}
	
public  List<GenericItem<String>> getBeneficiaryFollowees(String userId) {
		
		SearchRequest searchRequest = new SearchRequest("beneficiary_follow_idx");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.nestedQuery("user_follower", QueryBuilders.boolQuery().must(QueryBuilders.termQuery("user_follower.id", userId)), ScoreMode.None))
		.size(10)
		.sort(SortBuilders.fieldSort("follow_date").order(SortOrder.DESC));
		searchRequest.source(sourceBuilder);
		log.info("Inside User Beneficiary Service");
		try {
			RestHighLevelClient client = esConfig.getElasticsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			if(searchResponse.getHits().getTotalHits().value > 0L)
				return parseBeneficiarySearchHits(searchResponse);
			return new ArrayList<GenericItem<String>>();
		} catch (IOException e) {
			log.error("Error getting beneficiary followees for user: " + userId , e);
			return new ArrayList<GenericItem<String>>();
		}
	}
	
	private List<GenericItem<String>> parseBeneficiarySearchHits(SearchResponse searchResponse){
		
		List<GenericItem<String>> benFolloweeList = new ArrayList<GenericItem<String>>();
		BeneficiaryFollowItem benFolloweeItem;
		SearchHit[] searchHits = searchResponse.getHits().getHits();
		for (SearchHit searchHit : searchHits) {
			try {
				benFolloweeItem = new ObjectMapper().readValue(searchHit.getSourceAsString(), BeneficiaryFollowItem.class);
				benFolloweeList.add(benFolloweeItem.getBeneficiaryFollowee());
			}
			catch(Exception e) {
				log.info("Json Parsing Exception" + e);
				return new ArrayList<GenericItem<String>>();
			}
		}
		
		return benFolloweeList;
	}
	
}