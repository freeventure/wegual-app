package app.wegual.poc.es.service;

import java.io.IOException;

import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
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
import org.springframework.stereotype.Service;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.wegual.poc.es.messaging.SenderRunnable;
import app.wegual.poc.es.messaging.UserMessageSender;
import app.wegual.poc.es.model.User;
import app.wegual.poc.es.model.UserFollowers;
import app.wegual.poc.es.model.BeneficiaryFollowers;
import app.wegual.poc.es.model.GiveUpFollowers;
import app.wegual.poc.es.model.Timeline;

import org.elasticsearch.client.RequestOptions;

@Service
public class UserService {

	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private UserMessageSender ums;

	public void save(User user) throws IOException {
		System.out.println("Inside user service");
		IndexRequest request = new IndexRequest("user").id(user.getId())
				.source(new ObjectMapper().writeValueAsString(user), XContentType.JSON)
				.opType(DocWriteRequest.OpType.CREATE);

		System.out.println(user.getName());

		IndexResponse response = client.index(request, RequestOptions.DEFAULT);
		System.out.println(response.getId());

		if ((response.getResult().name()).equals("CREATED")) {
			Timeline userTimeline = new Timeline().withActorId(response.getId()).withTargetID(null)
					.withOperationType(response.getResult().name()).withTimestamp(new Date());
			sendMessageAsynch(userTimeline);
		}

	}

	public void followUser(UserFollowers userFollowers) throws IOException {
		System.out.println("Inside user service");
		IndexRequest request = new IndexRequest("userfollowers")
				.id(userFollowers.getUserFollowerId() + "$$" + userFollowers.getUserFolloweeId())
				.source(new ObjectMapper().writeValueAsString(userFollowers), XContentType.JSON)
				.opType(DocWriteRequest.OpType.CREATE);
		System.out.println(userFollowers.toString());

		IndexResponse response = client.index(request, RequestOptions.DEFAULT);
		System.out.println(response.getId());
		if ((response.getResult().name()).equals("CREATED")) {
			Timeline userTimeline = new Timeline().withActorId(userFollowers.getUserFollowerId())
					.withTargetID(userFollowers.getUserFolloweeId()).withOperationType("FOLLOW")
					.withTimestamp(new Date());
			sendMessageAsynch(userTimeline);
		}
	}

	public void followBeneficiary(BeneficiaryFollowers benFollowers) throws IOException {
		System.out.println("Inside user service");
		IndexRequest request = new IndexRequest("beneficiaryfollowers")
				.id(benFollowers.getUserFollowerId() + "$$" + benFollowers.getBeneficiaryFolloweeId())
				.source(new ObjectMapper().writeValueAsString(benFollowers), XContentType.JSON)
				.opType(DocWriteRequest.OpType.CREATE);
		System.out.println(benFollowers.toString());

		IndexResponse response = client.index(request, RequestOptions.DEFAULT);
		System.out.println(response.getId());
		if ((response.getResult().name()).equals("CREATED")) {
			Timeline userTimeline = new Timeline().withActorId(benFollowers.getUserFollowerId())
					.withTargetID(benFollowers.getBeneficiaryFolloweeId()).withOperationType("FOLLOW")
					.withTimestamp(new Date());
			sendMessageAsynch(userTimeline);
		}
	}

	public void followGiveUp(GiveUpFollowers giveUpFollowers) throws IOException {
		System.out.println("Inside user service");
		IndexRequest request = new IndexRequest("giveupfollowers")
				.id(giveUpFollowers.getUserFollowerId() + "$$" + giveUpFollowers.getGiveUpFolloweeId())
				.source(new ObjectMapper().writeValueAsString(giveUpFollowers), XContentType.JSON)
				.opType(DocWriteRequest.OpType.CREATE);
		System.out.println(giveUpFollowers.toString());

		IndexResponse response = client.index(request, RequestOptions.DEFAULT);
		System.out.println(response.getId());
		if ((response.getResult().name()).equals("CREATED")) {
			Timeline userTimeline = new Timeline().withActorId(giveUpFollowers.getUserFollowerId())
					.withTargetID(giveUpFollowers.getGiveUpFolloweeId()).withOperationType("FOLLOW")
					.withTimestamp(new Date());
			sendMessageAsynch(userTimeline);
		}
	}

	public long findUserFollowers(String userId) throws IOException {

		System.out.println("Inside user service");

		SearchRequest searchRequest = new SearchRequest("userfollowers");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.termQuery("userFolloweeId", userId));

		CardinalityAggregationBuilder aggregation = AggregationBuilders.cardinality("followers")
				.field("userFollowerId");
		sourceBuilder.aggregation(aggregation);
		searchRequest.source(sourceBuilder);

		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		System.out.println("total hits: " + searchResponse.getHits().getTotalHits());
		Cardinality cardinality = searchResponse.getAggregations().get("followers");

		return (cardinality.getValue());
	}

	public long findUserFollowing(String userId) throws IOException {

		System.out.println("Inside user service");

		SearchRequest searchRequest = new SearchRequest("userfollowers");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.termQuery("userFollowerId", userId));

		CardinalityAggregationBuilder aggregation = AggregationBuilders.cardinality("following")
				.field("userFolloweeId");
		sourceBuilder.aggregation(aggregation);
		searchRequest.source(sourceBuilder);

		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		System.out.println("total hits: " + searchResponse.getHits().getTotalHits());
		Cardinality cardinality = searchResponse.getAggregations().get("following");

		return (cardinality.getValue());
	}

	public long userTotal() throws IOException {

		System.out.println("Inside user service");

		CountRequest countRequest = new CountRequest("user");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.matchAllQuery());
		countRequest.source(sourceBuilder);

		CountResponse countResponse = client.count(countRequest, RequestOptions.DEFAULT);
		System.out.println(countResponse.getCount());
		return (countResponse.getCount());
	}

	protected void sendMessageAsynch(Timeline payload) {
		Thread th = new Thread(new SenderRunnable<UserMessageSender, Timeline>(ums, payload));
		th.start();
	}

}
