package com.wegual.pledgeservice;

import javax.annotation.PreDestroy;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;


@RefreshScope
@Configuration
public class ElasticSearchConfig {
	@Value("${elasticsearch.host}")
    private String elasticsearchHost;

	
	private RestHighLevelClient client;
	
	@PreDestroy
	protected void cleanUp() {
		if(client != null) {
			try {
				client.close();
			} catch (Exception ex) {
				//TODO: Handle logging here
			}
		}
	}
    
    public RestHighLevelClient getElastcsearchClient() {

    	if(client == null) {
    		client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(elasticsearchHost, 9200)));
    	}

        return client;
    }
}
