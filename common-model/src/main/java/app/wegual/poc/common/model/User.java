package app.wegual.poc.common.model;

import java.security.Timestamp;

import org.springframework.stereotype.Component;

@Component
public class User {

	private String id;
	private String name;
	private String email;
	private Timestamp lastLoggedInDate;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getLastLoggedInDate() {
		return lastLoggedInDate;
	}

	public void setLastLoggedInDate(Timestamp lastLoggedInDate) {
		this.lastLoggedInDate = lastLoggedInDate;
	}

}
