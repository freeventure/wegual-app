package app.wegual.common.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Beneficiary  implements Serializable {
	private static final long serialVersionUID = -867409585306111160L;
	
	@JsonProperty("beneficiary_id")
	private String id;
	
	@JsonProperty("full_name")
    private String name;
	
	private String username;
	
    private String description;
    
    private BeneficiaryType beneficiaryType;
    
    private String email;
    
    private String url;
    
    private String facebookPage;
    
    private String twitterHandle;
    
    private String linkedInPage;
    
    @JsonProperty("creation_stamp")
   	private Long createdTimestamp;
    
   	@JsonProperty("last_udate_stamp")
   	private Long updatedTimestamp;
    
   	@JsonProperty("picture_link")
    private String pictureLink;
    
   	@JsonIgnore
   	private String updated_by_userid;
   	
   	@JsonIgnore
   	private boolean account_locked;
   	
	@JsonIgnore
	@JsonProperty("is_active")
	private boolean is_active;
	
	@JsonIgnore
	private boolean email_verify_pending;
	
    public Beneficiary() {}
    
}
