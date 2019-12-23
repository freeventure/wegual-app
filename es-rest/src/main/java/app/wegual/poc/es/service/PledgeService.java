package app.wegual.poc.es.service;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkRequest;
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
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.Cardinality;
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

		IndexRequest request = new IndexRequest("pledge").id(pledge.getId())
				.source(new ObjectMapper().writeValueAsString(pledge), XContentType.JSON)
				.opType(DocWriteRequest.OpType.CREATE);
		IndexResponse response = client.index(request, RequestOptions.DEFAULT);
		System.out.println(response.getId());

		if ((response.getResult().name()).equals("CREATED")) {
			Timeline pledgeObjectForUser = new Timeline().withActorId(pledge.getUserId()).withTargetID(response.getId())
					.withOperationType("PLEDGE").withTimestamp(new Date());
			sendMessageAsynch(pledgeObjectForUser);
		}
		if ((response.getResult().name()).equals("CREATED")) {
			Timeline pledgeObjectForBeneficiary = new Timeline().withActorId(pledge.getBeneficiaryId())
					.withTargetID(response.getId()).withOperationType("PLEDGE").withTimestamp(new Date());
			sendMessageAsynch(pledgeObjectForBeneficiary);
		}
		if ((response.getResult().name()).equals("CREATED")) {
			Timeline pledgeObjectForGiveUp = new Timeline().withActorId(pledge.getGiveUpId())
					.withTargetID(response.getId()).withOperationType("PLEDGE").withTimestamp(new Date());
			sendMessageAsynch(pledgeObjectForGiveUp);
		}
	}
	

	public long usersTotalForBeneiciary(String beneficiaryId) throws IOException {

		System.out.println("Inside Pledge service");

		SearchRequest searchRequest = new SearchRequest("pledge");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.termQuery("beneficiaryId", beneficiaryId));

		CardinalityAggregationBuilder aggregation = AggregationBuilders.cardinality("UsersTotal").field("userId");

		sourceBuilder.aggregation(aggregation);
		searchRequest.source(sourceBuilder);

		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		System.out.println("total hits: " + searchResponse.getHits().getTotalHits().value);
		
		Cardinality agg = searchResponse.getAggregations().get("UsersTotal");

		return (agg.getValue());
	}

	public long giveUpTotalForBeneiciary(String beneficiaryId) throws IOException {

		System.out.println("Inside Pledge service");
		SearchRequest searchRequest = new SearchRequest("pledge");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.termQuery("beneficiaryId", beneficiaryId));

		CardinalityAggregationBuilder aggregation = AggregationBuilders.cardinality("GiveUpTotal").field("giveUpId");
		sourceBuilder.aggregation(aggregation);
		searchRequest.source(sourceBuilder);

		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		System.out.println("total hits: " + searchResponse.getHits().getTotalHits());
		Cardinality agg = searchResponse.getAggregations().get("GiveUpTotal");

		return (agg.getValue());
	}

	public long pledgesTotalForBeneiciary(String beneficiaryId) throws IOException {

		SearchRequest searchRequest = new SearchRequest("pledge");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.termQuery("beneficiaryId", beneficiaryId));
		searchRequest.source(sourceBuilder);

		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		System.out.println("total hits: " + searchResponse.getHits().getTotalHits().value);
		return (searchResponse.getHits().getTotalHits().value);
	}

	public long pledgeTotal() throws IOException {
		System.out.println("Inside Pledge service");
		CountRequest countRequest = new CountRequest("pledge");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.matchAllQuery());
		countRequest.source(sourceBuilder);

		CountResponse countResponse = client.count(countRequest, RequestOptions.DEFAULT);
		System.out.println(countResponse.getCount());
		return (countResponse.getCount());
	}

	public void amountTotalForBeneficiary(String beneficiaryId) {

	}

	protected void sendMessageAsynch(Timeline payload) {
		Thread th = new Thread(new SenderRunnable<PledgeMessageSender, Timeline>(pms, payload));
		th.start();
	}

}
