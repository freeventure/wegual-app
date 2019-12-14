package app.wegual.poc.indices;

import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Component;

@Component
public class Beneficiary {
	
	private String id;
	private String name;
	private String description;
	private String beneficiaryType;
	private String url;
	private String facebookPage;
	private String twitterHandle;
	private String linkedInPage;
	private String owner_id;
	private String owner_name;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBeneficiaryType() {
		return beneficiaryType;
	}

	public void setBeneficiaryType(String beneficiaryType) {
		this.beneficiaryType = beneficiaryType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFacebookPage() {
		return facebookPage;
	}

	public void setFacebookPage(String facebookPage) {
		this.facebookPage = facebookPage;
	}

	public String getTwitterHandle() {
		return twitterHandle;
	}

	public void setTwitterHandle(String twitterHandle) {
		this.twitterHandle = twitterHandle;
	}

	public String getLinkedInPage() {
		return linkedInPage;
	}

	public void setLinkedInPage(String linkedInPage) {
		this.linkedInPage = linkedInPage;
	}

	public String getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}

	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}
/*
	public void createBeneficiaryIndex() {
		
		CreateIndexRequest request = new CreateIndexRequest("beneficiary");
		request.mapping(
		        "{\n" +
		        "  \"properties\": {\n" +
		        "    \"name\": {\n" +
		        "      \"type\": \"text\"\n" +
		        "    \"description\": {\n" +
		        "      \"type\": \"text\"\n" +
		        "    \"beneficiaryType\": {\n" +
		        "      \"type\": \"keyword\"\n" +
		        "    \"url\": {\n" +
		        "      \"type\": \"keyword\"\n" +
		        "    \"facebookPage\": {\n" +
		        "      \"type\": \"keyword\"\n" +
		        "    \"twitterHandle\": {\n" +
		        "      \"type\": \"keyword\"\n" +
		        "    \"linkedInPage\": {\n" +
		        "      \"type\": \"keyword\"\n" +
		        "    \"owner_id\": {\n" +
		        "      \"type\": \"keyword\"\n" +
		        "    \"owner_name\": {\n" +
		        "      \"type\": \"text\"\n" +
		        "    }\n" +
		        "  }\n" +
		        "}", 
		        XContentType.JSON);
	}
	*/
}
