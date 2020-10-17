package app.wegual.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetails {
	
	private String userId;
	private String city;
	private String state;
	private String country;
	private String baseCurrency;
}
