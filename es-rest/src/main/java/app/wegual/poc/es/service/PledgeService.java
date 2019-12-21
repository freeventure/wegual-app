package app.wegual.poc.es.service;

import java.io.IOException;
import java.util.Date;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Stats;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.wegual.poc.es.messaging.PledgeMessageSender;
import app.wegual.poc.es.messaging.SenderRunnable;
import app.wegual.poc.es.model.Pledge;
import app.wegual.poc.es.model.Timeline;

@Service
public class PledgeService {
	
	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private PledgeMessageSender pms;

	public void save(Pledge pledge) throws IOException {
		System.out.println("Inside Pledge service");
		IndexRequest request = new IndexRequest("pledge")
				.source(new ObjectMapper().writeValueAsString(pledge), XContentType.JSON);

		IndexResponse response = client.index(request, RequestOptions.DEFAULT);
		System.out.println(response.getId());
	
		if ((response.getResult().name()).equals("CREATED")) {
			Timeline pledgeObjectForUser = new Timeline()
					.withActorId(pledge.getUser_id())
					.withTargetID(response.getId())
					.withOperationType("PLEDGE")
					.withTimestamp(new Date());
			sendMessageAsynch(pledgeObjectForUser);
		}
		if ((response.getResult().name()).equals("CREATED")) {
			Timeline pledgeObjectForBeneficiary = new Timeline()
					.withActorId(pledge.getBeneficiary_id())
					.withTargetID(response.getId())
					.withOperationType("PLEDGE")
					.withTimestamp(new Date());
			sendMessageAsynch(pledgeObjectForBeneficiary);
		}
		if ((response.getResult().name()).equals("CREATED")) {
			Timeline pledgeObjectForGiveUp = new Timeline()
					.withActorId(pledge.getGiveUp_id())
					.withTargetID(response.getId())
					.withOperationType("PLEDGE")
					.withTimestamp(new Date());
			sendMessageAsynch(pledgeObjectForGiveUp);
		}
	}
	
	public void usersTotalForBeneiciary(String beneficiaryId) throws IOException{
		SearchRequest searchRequest = new SearchRequest("pledge");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.termQuery("beneficiary_id", "beneficiaryId"));
		CardinalityAggregationBuilder aggregation =
		        AggregationBuilders
		                .cardinality("UsersTotal")
		                .field("user_id");
		sourceBuilder.aggregation(aggregation);
		searchRequest.source(sourceBuilder); 
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		System.out.println("total hits: " + searchResponse.getHits().getTotalHits());
	}

	public void giveUpTotalForBeneiciary(String beneficiaryId) {
		SearchRequest searchRequest = new SearchRequest("pledge");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.termQuery("beneficiary_id", "beneficiaryId"));
		CardinalityAggregationBuilder aggregation =
		        AggregationBuilders
		                .cardinality("GiveUpTotal")
		                .field("giveUp_id");
		sourceBuilder.aggregation(aggregation);
		searchRequest.source(sourceBuilder); 
	}
	
	public void pledgesTotalForBeneiciary(String beneficiaryId) {
		SearchRequest searchRequest = new SearchRequest("pledge");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.termQuery("beneficiary_id", "beneficiaryId"));
		searchRequest.source(sourceBuilder); 
	}
	
	public void amountTotalForBeneficiary(String beneficiaryId) {
		
	}
	protected void sendMessageAsynch(Timeline payload){
		Thread th = new Thread(new SenderRunnable<PledgeMessageSender, Timeline>(pms, payload));
		th.start();
	}

}
