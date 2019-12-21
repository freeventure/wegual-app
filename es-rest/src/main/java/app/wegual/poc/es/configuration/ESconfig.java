package app.wegual.poc.es.configuration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ESconfig {

	@Bean
	public RestHighLevelClient client() {
	RestClientBuilder builder =RestClient.builder(new HttpHost("192.168.56.101", 9200, "http"));
	       RestHighLevelClient client = new RestHighLevelClient(builder);
	       return client;
	}
}