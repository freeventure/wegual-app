package app.wegual.poc.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ESConfig {

	@Bean
	public RestHighLevelClient client() {
		RestClientBuilder builder = RestClient.builder(new HttpHost("http://192.168.56.101", 9200, "http"));
	    RestHighLevelClient client = new RestHighLevelClient(builder);
	    return client;
	}

}

