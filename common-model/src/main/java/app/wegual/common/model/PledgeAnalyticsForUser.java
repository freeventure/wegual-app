package app.wegual.common.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PledgeAnalyticsForUser implements Serializable{

	private static final long serialVersionUID = -392491560310169586L;
	
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
