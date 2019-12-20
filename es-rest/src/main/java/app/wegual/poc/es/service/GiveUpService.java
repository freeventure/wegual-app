package app.wegual.poc.es.service;

import java.io.IOException;
import java.util.Date;

import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.wegual.poc.es.messaging.SenderRunnable;
import app.wegual.poc.es.messaging.GiveUpMessageSender;
import app.wegual.poc.es.model.Timeline;
import app.wegual.poc.es.model.GiveUp;

@Service
public class GiveUpService {
	
	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private GiveUpMessageSender gms;

	public void save(GiveUp giveUp) throws IOException {
		System.out.println("Inside GiveUp service");
		IndexRequest request = new IndexRequest("giveup")
				.source(new ObjectMapper().writeValueAsString(giveUp), XContentType.JSON);

		System.out.println(giveUp.getName());
		IndexResponse response = client.index(request, RequestOptions.DEFAULT);
		System.out.println(response.getId());
		if ((response.getResult().name()).equals("CREATED")) {
			Timeline giveUpTimeline = new Timeline()
					.withActorId(response.getId())
					.withTargetID(null)
					.withOperationType(response.getResult().name())
					.withTimestamp(new Date());
			sendMessageAsynch(giveUpTimeline);
		}

	}
	
	protected void sendMessageAsynch(Timeline payload)
	{
		Thread th = new Thread(new SenderRunnable<GiveUpMessageSender, Timeline>(gms, payload));
		th.start();
	}


}
