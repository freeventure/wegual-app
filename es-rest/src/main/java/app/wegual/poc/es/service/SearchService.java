package app.wegual.poc.es.service;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

	@Autowired
	private RestHighLevelClient client;

	
	public void beneficiarySuggestor(String prefix) {
		
	}
}
