package app.wegual.poc.es.service;

import java.io.IOException;
import java.util.Date;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
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

		System.out.println(pledge.getAmount());
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
	
	protected void sendMessageAsynch(Timeline payload)
	{
		Thread th = new Thread(new SenderRunnable<PledgeMessageSender, Timeline>(pms, payload));
		th.start();
	}

}
