package app.wegual.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PledgeAnalyticsForUser {

	private long pledge;
	private long beneficiary;
	private long giveup;
	
	public PledgeAnalyticsForUser() {
		
	}
	public PledgeAnalyticsForUser(long pledge, long beneficiary, long giveup) {
		super();
		this.pledge = pledge;
		this.beneficiary = beneficiary;
		this.giveup = giveup;
	}
	
	
}
