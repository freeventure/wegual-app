package app.wegual.common.model;

import java.io.Serializable;

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
    
    @JsonProperty("creation_stamp")
	private long createdTimestamp;
    
    @JsonProperty("last_udate_stamp")
   	private long updatedTimestamp;
   	
   	private String pictureLink;
    
    public User() {}
    
	public User(String username, String email) {
		super();
		this.username = username;
		this.email = email;
	}
}