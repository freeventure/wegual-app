package app.wegual.poc.user.configuration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RefreshScope
@Configuration
public class UserConfig {

	@Value("${elasticsearchHosts:192.168.111.4}")
	private String elasticsearchHosts;
	
	@Bean
	public RestHighLevelClient client() {
	
		String[] hosts = elasticsearchHosts.split(":");
		HttpHost[] httpHost = new HttpHost[hosts.length];
		for (int i = 0; i < hosts.length; i ++) {
			httpHost[i] = new HttpHost(hosts[i],9200);
		}
		
		RestClientBuilder builder = RestClient.builder(httpHost);
		RestHighLevelClient client = new RestHighLevelClient(builder);
		return client;
	}
}
