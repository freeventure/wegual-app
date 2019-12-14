package app.wegual.poc.controllers;

import java.io.IOException;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.wegual.poc.indices.User;

@RestController
@RequestMapping(path = "/users")

public class UserController {
	
	@Autowired
    private RestHighLevelClient client;
	
	@PostMapping("/userIndex")
    public void userIndex() throws IOException {
		
		System.out.print("hello");
		CreateIndexRequest request = new CreateIndexRequest("users");
		request.mapping(
		        "{\n" +
		        "  \"properties\": {\n" +
		        "    \"name\": {\n" +
		        "      \"type\": \"text\"\n" +
		        "    \"email\": {\n" +
		        "      \"type\": \"keyword\"\n" +
		        "    }\n" +
		        "  }\n" +
		        "}", 
		        XContentType.JSON);
		
		GetIndexRequest getIndexRequest = new GetIndexRequest("users");
        boolean exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        if(!exists){
            CreateIndexResponse indexResponse = client.indices().create(request, RequestOptions.DEFAULT);
            System.out.println("response id: "+indexResponse.index());
        }
        else
        	System.out.println("already exist");
	}
	
	@PostMapping("/")
	public String save(@RequestBody User user) throws IOException{
		
		IndexRequest request = new IndexRequest("users");
		request.id(user.getId());
		request.source(new ObjectMapper().writeValueAsString(user), XContentType.JSON);
		
		IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
		System.out.println("response id: "+indexResponse.getId());
		return indexResponse.getResult().name();
		
		
	}
        
        ActionListener listener = new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                System.out.println(" Document updated successfully !!!");
            }

            @Override
            public void onFailure(Exception e) {
                System.out.print(" Document creation failed !!!"+ e.getMessage());
            }
        };
}
