package app.wegual.common.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEmailVerifyToken implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("user_id")
	private String userId;
	
    private String token;
    private int attempts;
    private long validity;
    private String email;
    private String secret;
    
    @JsonProperty("not_before")
    private long notBefore;
    
    @JsonProperty("creation_stamp")
    private long createdTimestamp;
    
    public static UserEmailVerifyToken with(String email) {
    	UserEmailVerifyToken uevt = new UserEmailVerifyToken();
    	uevt.setEmail(email);
    	uevt.setCreatedTimestamp(System.currentTimeMillis());
    	// expiry to 5 minutes from creation
    	uevt.setValidity(uevt.getCreatedTimestamp() + 1000*60*5);
    	return uevt;
    }
}
