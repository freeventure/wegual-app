package app.wegual.poc.es.service;

import java.io.IOException;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.wegual.poc.es.model.Timeline;

@Service
public class GiveUpTimelineService {
	@Autowired
	private RestHighLevelClient client;
	
	public void save(Timeline bt) throws IOException {
		
		IndexRequest request = new IndexRequest("giveuptimeline") 
				.source(new ObjectMapper().writeValueAsString(bt), XContentType.JSON);
		//System.out.println("BeneficiaryTimeline object created");
		IndexResponse response = client.index(request,RequestOptions.DEFAULT );
		System.out.println(response.getResult().name());
	}
}
