package app.wegual.poc.beneficiary.service;

import java.io.IOException;
import java.util.Date;

import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.Cardinality;
import org.elasticsearch.search.aggregations.metrics.CardinalityAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.wegual.poc.beneficiary.messaging.BeneficiaryMessageSender;
import app.wegual.poc.common.messaging.SenderRunnable;
import app.wegual.poc.common.model.Beneficiary;
import app.wegual.poc.common.model.Timeline;

@Service
public class BeneficiaryService {
	
	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private BeneficiaryMessageSender bms;

	@Autowired
	@Qualifier("senderPool")
	private TaskExecutor te;

	public void save(Beneficiary ben) throws IOException {
		System.out.println("Inside beneficiary service");
		IndexRequest request = new IndexRequest("beneficiary").id(ben.getId())
				.source(new ObjectMapper().writeValueAsString(ben), XContentType.JSON)
				.opType(DocWriteRequest.OpType.CREATE);

		System.out.println(ben.getName());
		IndexResponse response = client.index(request, RequestOptions.DEFAULT);
		System.out.println(response.getId());
		if ((response.getResult().name()).equals("CREATED")) {
			Timeline benTimeline = new Timeline().withActor(response.getId(),ben.getName(),"BENEFICIARY")
					.withActionType(response.getResult().name())
					.withTimestamp(new Date());
			sendMessageAsynch(benTimeline);
		}

	}
	public Beneficiary getBeneficiaryById(String id) throws IOException {
		SearchRequest searchRequest = new SearchRequest("beneficiary");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.termQuery("id", id));
		searchRequest.source(sourceBuilder);

		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		Beneficiary ben=null;
		for(SearchHit searchHit : searchResponse.getHits().getHits()){
		Beneficiary beneficiary = new ObjectMapper().readValue(searchHit.getSourceAsString(),Beneficiary.class);
		ben = beneficiary;
		}
		return ben;
	}

	public long beneficiaryTotal() throws IOException {

		System.out.println("Inside beneficiary service");

		CountRequest countRequest = new CountRequest("beneficiary");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.matchAllQuery());
		countRequest.source(sourceBuilder);

		CountResponse countResponse = client.count(countRequest, RequestOptions.DEFAULT);
		System.out.println(countResponse.getCount());
		return (countResponse.getCount());
	}

	public long findBeneficiaryFollowers(String beneficiaryId) throws IOException {

		System.out.println("Inside beneficiary service");

		SearchRequest searchRequest = new SearchRequest("beneficiaryfollowers");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.termQuery("beneficiaryFolloweeId", beneficiaryId));

		CardinalityAggregationBuilder aggregation = AggregationBuilders.cardinality("followers")
				.field("userFollowerId");
		sourceBuilder.aggregation(aggregation);
		searchRequest.source(sourceBuilder);

		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		System.out.println("total hits: " + searchResponse.getHits().getTotalHits());
		Cardinality cardinality = searchResponse.getAggregations().get("followers");

		return (cardinality.getValue());
	}

	public Aggregations topBeneficiariesByPledge() throws IOException {
		System.out.println("Inside beneficiary service");

		SearchRequest searchRequest = new SearchRequest("pledge");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		AggregationBuilder aggregation = AggregationBuilders.terms("topBeneficiary").field("benenficiaryId")
				.subAggregation(AggregationBuilders.sum("total").field("amount"));
		sourceBuilder.aggregation(aggregation);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		return searchResponse.getAggregations();
	}

	protected void sendMessageAsynch(Timeline payload) {
		te.execute(new SenderRunnable<BeneficiaryMessageSender, Timeline>(bms, payload));
	}
}
