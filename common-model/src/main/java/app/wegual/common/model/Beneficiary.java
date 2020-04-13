package app.wegual.common.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Beneficiary  implements Serializable {
	private static final long serialVersionUID = -867409585306111160L;
	
	@JsonProperty("beneficiary_id")
	private Long id;
	
	@JsonProperty("beneficiary")
    private String name;
	
    private String description;
    
    @JsonProperty("beneficiary_type")
    private BeneficiaryType beneficiaryType;
    
    @JsonIgnore
    private String url;
    
    @JsonIgnore
    private String facebookPage;
    
    @JsonIgnore
    private String twitterHandle;
    
    @JsonIgnore
    private String linkedInPage;

    @JsonProperty("owner_id")
    private Long owner;
    
    @JsonProperty("created_date")
	private Long createdDate;
    
    @JsonProperty("updated_date")
   	private Long updatedDate;
	
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public BeneficiaryType getBeneficiaryType() {
		return beneficiaryType;
	}

	public void setBeneficiaryType(BeneficiaryType beneficiaryType) {
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

	public Long getOwner() {
		return owner;
	}

	public void setOwner(Long owner) {
		this.owner = owner;
	}

	public Long getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Long creationDate) {
		this.createdDate = creationDate;
	}

	public Long getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Long updationDate) {
		this.updatedDate = updationDate;
	}
}
