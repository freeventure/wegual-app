package app.wegual.poc.common.model;

import java.io.Serializable;
//import java.security.Timestamp;
import java.sql.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

@Entity 
public class Beneficiary  implements Serializable {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(unique=true, nullable=false) 
    private String name;
    
    private String description;
    
    @Column(nullable=false) 
    @Enumerated(EnumType.STRING)
    private BeneficiaryType beneficiaryType;
    
    @Column(unique=true, nullable=false)
    private String url;
    
    
    private String facebookPage;
    private String twitterHandle;
    
    private String linkedInPage;

    @OneToOne
    @JoinColumn(name="OWNER_ID", nullable=false, insertable=true, updatable=true)
    private User owner;
    
    @CreationTimestamp
	private Timestamp creationDate;
    
    @CreationTimestamp
   	private Timestamp updationDate;
	
    
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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getUpdationDate() {
		return updationDate;
	}

	public void setUpdationDate(Timestamp updationDate) {
		this.updationDate = updationDate;
	}

	
	

}
