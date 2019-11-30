package app.wegual.poc.es.index;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class IndexCreation implements CommandLineRunner {

	public static String USER_FOLLOW_INDEX = "user_follow_idx";
	@Autowired
	RestHighLevelClient client;
	
	@Override
	public void run(String... args) throws Exception {
		if(client != null)
		{
			System.out.println("Client is not null");
			List<String> indices = new IndexListReader().getIndices();
			List<String> verified = new IndexVerifier().verifyIndices(client, indices);
			if(indices.size() != verified.size())
				throw new Exception("Could not verify indices. Check index.list and corresponding json files in classpath");
		}
		
	}

}

class IndexListReader {
	
	public List<String> getIndices() {
		List<String> indices = new ArrayList<String>(); 
		InputStream fileInputStream = this.getClass() 
				.getClassLoader().getResourceAsStream("index/index.list");
		try (Scanner scanner = new Scanner(fileInputStream)) {
			while (scanner.hasNextLine()) {
			   String line = scanner.nextLine();
			   if(!StringUtils.isEmpty(line))
				   // process the line
				  indices.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return indices;
	}
}

class IndexVerifier {
	
	public List<String> verifyIndices(RestHighLevelClient client, List<String> indices) {
		
		List<String> verifiedIndices = new ArrayList<String>(); 
		
		for(String index : indices) {
			try {
				
				if(verifyIndex(client, index))
					verifiedIndices.add(index);
			} catch (Exception ex) {
				System.out.println("Count not verify index: " + index);
			}
		}
		return verifiedIndices;
	}
	
	public boolean verifyIndex(RestHighLevelClient client, String index) {
		String jsonContent = null;
		String indexName = index + "_idx";
		try {

			jsonContent = readFileFromClasspath("index/json/" + indexName + ".json");
			GetIndexRequest request = new GetIndexRequest();
			request.indices(indexName);
			request.local(false); 
			request.humanReadable(true); 
			request.includeDefaults(false); 
			
			boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
			if(!exists)
			{
				System.out.println("Index does not exist: " + indexName);
				CreateIndexRequest indexRequest = new CreateIndexRequest(indexName);
				indexRequest.timeout("2m"); 
				
				indexRequest.source(jsonContent, XContentType.JSON);
				CreateIndexResponse createIndexResponse = client.indices().create(indexRequest, RequestOptions.DEFAULT);
				
				if(createIndexResponse.isAcknowledged())
					System.out.println("Index created: " + index);
			} 		
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public String readFileFromClasspath(final String fileName) throws IOException, URISyntaxException {
	    return new String(Files.readAllBytes(
	                Paths.get(getClass().getClassLoader()
	                        .getResource(fileName)
	                        .toURI())));
	}	
}

