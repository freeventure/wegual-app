package app.wegual.poc.pledge.service;

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
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.Cardinality;
import org.elasticsearch.search.aggregations.metrics.CardinalityAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.wegual.poc.common.messaging.SenderRunnable;
import app.wegual.poc.common.model.Pledge;
import app.wegual.poc.common.model.Timeline;
import app.wegual.poc.pledge.messaging.PledgeMessageSender;

@Service
public class PledgeService {

	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private PledgeMessageSender pms;
	
	@Autowired
	@Qualifier("senderPool")
	private TaskExecutor te;
	
//	@Autowired
//	private UserService us;
//	
//	@Autowired
//	private BeneficiaryService ben;
//	
//	@Autowired
//	private GiveUpService gup;

	public void save(Pledge pledge) throws IOException {
		System.out.println("Inside Pledge service");

		IndexRequest request = new IndexRequest("pledge").id(pledge.getId())
				.source(new ObjectMapper().writeValueAsString(pledge), XContentType.JSON)
				.opType(DocWriteRequest.OpType.CREATE);

		IndexResponse response = client.index(request, RequestOptions.DEFAULT);
		System.out.println(response.getId());
//		if ((response.getResult().name()).equals("CREATED")) {
//			Timeline timeline = new Timeline().withActionId(pledge.getId())
//					.withActor(pledge.getUserId(),us.getUserById(pledge.getUserId()).getName(), "USER")
//					.withTarget(pledge.getBeneficiaryId(), ben.getBeneficiaryById(pledge.getBeneficiaryId()).getName(), "BENEFICIRY")
//					.withOptionalTarget(pledge.getGiveUpId(),gup.getGiveUpById(pledge.getGiveUpId()).getName() , "GIVEUP")
//					.withActionType("PLEDGE")
//					.withTimestamp(new Date());
//			sendMessageAsynch(timeline);
//		}
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

	public double amountTotalForBeneficiary(String beneficiaryId) {
		System.out.println("Inside Pledge service");
		return 0.0;
	}

	protected void sendMessageAsynch(Timeline payload) {
		te.execute(new SenderRunnable<PledgeMessageSender, Timeline>(pms, payload));
	}

}

