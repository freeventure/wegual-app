package app.wegual.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeneficiaryProfileCounts {
	
	private long followers;
	private long pledges;
	private long benefactors;
	
	public BeneficiaryProfileCounts() {
		
	}
	
	public BeneficiaryProfileCounts(long followers, long pledges, long benefactors) {
		super();
		this.followers = followers;
		this.pledges = pledges;
		this.benefactors = benefactors;

	}
	

}
