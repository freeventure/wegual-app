package app.wegual.common.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User implements Serializable {
	private static final long serialVersionUID = -4545977887269286250L;

	@JsonProperty("user_id")
	private String id;

    private String username;
    
    @JsonProperty("first_name")
    private String firstName;
    
    @JsonProperty("last_name")
    private String lastName;
    
    private String email;
    
    @JsonProperty("creation_stamp")
	private long createdTimestamp;
    
    @JsonProperty("last_udate_stamp")
   	private long updatedTimestamp;
   	
    @JsonProperty("picture_link")
   	private String pictureLink;
   	
    @JsonProperty("account_locked")
   	private Boolean accountLocked;

    @JsonProperty("is_active")
   	private Boolean active;
    
    @JsonProperty("full_name")
    @JsonIgnore
    private String fullName;

    @JsonProperty("email_verify_pending")
   	private Boolean emailVerifyPending;
   	
    public User() {}
    
	public User(String username, String email) {
		super();
		this.username = username;
		this.email = email;
	}

}