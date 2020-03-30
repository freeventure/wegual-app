package app.wegual.common.model;

import java.io.Serializable;

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
    
    public User() {}
    
	public User(String username, String email) {
		super();
		this.username = username;
		this.email = email;
	}
}