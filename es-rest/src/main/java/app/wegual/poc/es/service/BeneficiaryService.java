package app.wegual.poc.es.service;

import java.io.IOException;

import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.wegual.poc.common.util.MessagingConstants;
import app.wegual.poc.es.messaging.BeneficiaryMessageSender;
import app.wegual.poc.es.messaging.MessageSender;
import app.wegual.poc.es.messaging.SenderRunnable;
import app.wegual.poc.es.model.Beneficiary;
import app.wegual.poc.es.model.Timeline;

import org.elasticsearch.client.RequestOptions;

@Service
public class BeneficiaryService {

	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private BeneficiaryMessageSender bms;

//	@Autowired
//	private ActionListener<IndexResponse> listener;

	public void save(Beneficiary ben) throws IOException {
		System.out.println("Inside beneficiary service");
		IndexRequest request = new IndexRequest("beneficiary").id(ben.getId())
				.source(new ObjectMapper().writeValueAsString(ben), XContentType.JSON)
				.opType(DocWriteRequest.OpType.CREATE);

		System.out.println(ben.getName());
		IndexResponse response = client.index(request, RequestOptions.DEFAULT);
		System.out.println(response.getId());
		if ((response.getResult().name()).equals("CREATED")) {
			Timeline benTimeline = new Timeline().withActorId(response.getId()).withTargetID(null)
					.withOperationType(response.getResult().name()).withTimestamp(new Date());
			sendMessageAsynch(benTimeline);
		}

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

	protected void sendMessageAsynch(Timeline payload) {
		Thread th = new Thread(new SenderRunnable<BeneficiaryMessageSender, Timeline>(bms, payload));
		th.start();
	}

}
