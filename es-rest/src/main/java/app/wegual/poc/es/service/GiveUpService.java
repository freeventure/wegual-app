package app.wegual.poc.es.service;

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
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.Cardinality;
import org.elasticsearch.search.aggregations.metrics.CardinalityAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.wegual.poc.es.messaging.SenderRunnable;
import app.wegual.poc.es.messaging.GiveUpMessageSender;
import app.wegual.poc.es.model.Timeline;
import app.wegual.poc.es.model.User;
import app.wegual.poc.es.model.Beneficiary;
import app.wegual.poc.es.model.GiveUp;

@Service
public class GiveUpService {

	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private GiveUpMessageSender gms;

	@Autowired
	@Qualifier("senderPool")
	private TaskExecutor te;
	
	public void save(GiveUp giveUp) throws IOException {
		System.out.println("Inside GiveUp service");
		IndexRequest request = new IndexRequest("giveup").id(giveUp.getName())
				.source(new ObjectMapper().writeValueAsString(giveUp), XContentType.JSON)
				.opType(DocWriteRequest.OpType.CREATE);

		System.out.println(giveUp.getName());

		IndexResponse response = client.index(request, RequestOptions.DEFAULT);

		System.out.println(giveUp.getName());

		if ((response.getResult().name()).equals("CREATED")) {
			Timeline giveUpTimeline = new Timeline().withActor(response.getId(),giveUp.getName(),"GIVEUP")
					.withActionType(response.getResult().name())
					.withTimestamp(new Date());
			sendMessageAsynch(giveUpTimeline);
		}

	}

	public long pledgesTotalForGiveUp(String giveUpId) throws IOException {

		System.out.println("Inside GiveUp Service");

		SearchRequest searchRequest = new SearchRequest("pledge");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.termQuery("giveUpId", giveUpId));
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		System.out.println(searchResponse.getHits().getTotalHits().value);
		return (searchResponse.getHits().getTotalHits().value);

	}
	
	public GiveUp getGiveUpById(String id) throws IOException {
		SearchRequest searchRequest = new SearchRequest("giveup");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.termQuery("id", id));
		searchRequest.source(sourceBuilder);

		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		GiveUp gup = null;
		for(SearchHit searchHit : searchResponse.getHits().getHits()){
		GiveUp giveup = new ObjectMapper().readValue(searchHit.getSourceAsString(),GiveUp.class);
		gup = giveup;
		}
		return gup;
	}


	public long usersTotalForGiveup(String giveUpId) throws IOException {

		System.out.println("Inside GiveUp Service");

		SearchRequest searchRequest = new SearchRequest("pledge");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.termQuery("giveUpId", giveUpId));
		
		CardinalityAggregationBuilder aggregation = AggregationBuilders.cardinality("agg").field("userId");
		sourceBuilder.aggregation(aggregation);
		
		searchRequest.source(sourceBuilder);
		
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		
		Cardinality cardinality = searchResponse.getAggregations().get("agg");
		System.out.println(cardinality.getValue());
		
		return (cardinality.getValue());
	}

	public long beneficiaryTotalForGiveup(String giveUpId) throws IOException {

		System.out.println("Inside GiveUp Service");

		SearchRequest searchRequest = new SearchRequest("pledge");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.termQuery("giveUpId", giveUpId));
		
		CardinalityAggregationBuilder aggregation = AggregationBuilders.cardinality("agg").field("beneficiaryId");
		sourceBuilder.aggregation(aggregation);
		
		searchRequest.source(sourceBuilder);
		
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		Cardinality cardinality = searchResponse.getAggregations().get("agg");
		System.out.println(cardinality.getValue());
		return (cardinality.getValue());
	}

	public long giveUpTotal() throws IOException {

		System.out.println("Inside GiveUp Service");

		CountRequest countRequest = new CountRequest("giveup");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.matchAllQuery());
		countRequest.source(sourceBuilder);

		CountResponse countResponse = client.count(countRequest, RequestOptions.DEFAULT);
		System.out.println(countResponse.getCount());
		return (countResponse.getCount());

	}

	protected void sendMessageAsynch(Timeline payload) {
		te.execute(new SenderRunnable<GiveUpMessageSender, Timeline>(gms, payload));
	}

}
