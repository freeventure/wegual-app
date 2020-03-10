package com.wegual.userservice;

import javax.annotation.PreDestroy;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

//@RefreshScope
//@Configuration
public class ElasticSearchConfig {
	@Value("${elasticsearch.hosts}")
    private String elasticsearchHosts;

	
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
    		String[] hosts = elasticsearchHosts.split(":");
    		HttpHost[] httpHosts = new HttpHost[hosts.length];
    		for(int i=0; i < hosts.length; i++)
    			httpHosts[i] = new HttpHost(hosts[i], 9200);
    		client = new RestHighLevelClient(
                //RestClient.builder(new HttpHost(elasticsearchHost, 9200))
                RestClient.builder(httpHosts)
                );
    	}

        return client;
    }
}
