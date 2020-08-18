package app.wegual.common.model;

import java.io.Serializable;
import java.util.Currency;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User implements Serializable {
	private static final long serialVersionUID = -4545977887269286250L;

	private String id;

    private String username;
    
    private String firstName;
    
    private String lastName;
    
    private String email;
    
	private long createdTimestamp;
    
   	private long updatedTimestamp;
   	
   	private String pictureLink;
   	
   	@JsonProperty("account_locked")
   	private Boolean accountLocked;

   	@JsonProperty("is_active")
   	private Boolean active;

   	@JsonProperty("email_verify_pending")
   	private Boolean emailVerifyPending;
   	
   	private Location location;
   	
   	@JsonProperty("base_currency")
   	private Currency baseCurrency;
   	
   	@JsonProperty("filled_details")
   	private boolean filledDetails;
   	
    public User() {}
    
	public User(String username, String email) {
		super();
		this.username = username;
		this.email = email;
	}

}