package com.wegual.giveupservice;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.wegual.common.model.GenericItem;
import app.wegual.common.model.GiveUp;
import app.wegual.common.model.User;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class GiveUpUtils {
	
	@Autowired
	private ElasticSearchConfig esConfig;
	
	public GenericItem<String> getUserGenericItem(String userId) {
		GenericItem<String> user = new GenericItem<String>();
		try {
			System.out.println("Searching for user with id" + userId);
			SearchRequest searchRequest = new SearchRequest("user_idx");
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
			sourceBuilder.query(QueryBuilders.termQuery("user_id", userId));
			searchRequest.source(sourceBuilder);
			
			RestHighLevelClient client = esConfig.getElasticsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			
			User u = null;
			if(searchResponse.getHits().getTotalHits().value>0L) {
				for(SearchHit hit : searchResponse.getHits()) {
					u = new ObjectMapper().readValue(hit.getSourceAsString(), User.class);
				}
				user.setId(u.getId());
				String name = u.getFirstName() + " " + u.getLastName();
				user.setName(name);
				user.setPictureLink(u.getPictureLink());
				String permalink = "/user/" + u.getId();
				user.setPermalink(permalink);
				return user;
			}
			
		} catch (Exception e) {
			log.info("User not found", e);
		}
		return user;
	}
	
	public GenericItem<String> getGiveUpGenericItem(String giveupId) {
		GenericItem<String> giveUp = new GenericItem<String>();
		try {
			System.out.println("Searching for giveup with id" + giveupId);
			SearchRequest searchRequest = new SearchRequest("giveup_idx");
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
			sourceBuilder.query(QueryBuilders.termQuery("giveup_id", giveupId));
			searchRequest.source(sourceBuilder);
			
			RestHighLevelClient client = esConfig.getElasticsearchClient();
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			
			GiveUp g = null;
			if(searchResponse.getHits().getTotalHits().value>0L) {
				for(SearchHit hit : searchResponse.getHits()) {
					g = new ObjectMapper().readValue(hit.getSourceAsString(), GiveUp.class);
				}
			}
			giveUp.setId(g.getId());
			giveUp.setName(g.getName());
			giveUp.setPictureLink(g.getPictureLink());
			String permaLink = "/giveup/" + g.getId();
			giveUp.setPermalink(permaLink);
			return giveUp;
			
		} catch (Exception e) {
			log.info("GiveUp not found", e);
		}
		return giveUp;
	}

}