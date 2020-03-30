package app.wegual.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileCounts {
	
	private long followers;
	private long following;
	private long beneficiaries;
	private long giveups;
	
	public UserProfileCounts() {
		
	}
	
	public UserProfileCounts(long followers, long following, long beneficiaries, long giveups) {
		super();
		this.followers = followers;
		this.following = following;
		this.beneficiaries = beneficiaries;
		this.giveups = giveups;
	}



	public UserProfileCounts(long followers, long following) {
		super();
		this.followers = followers;
		this.following = following;
	}
	
}
