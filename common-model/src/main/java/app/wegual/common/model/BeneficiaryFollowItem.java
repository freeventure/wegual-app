package app.wegual.common.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeneficiaryFollowItem{
	
	private String id;

	@JsonProperty("user_follower")
	private GenericItem<String> userFollower;
		
	@JsonProperty("beneficiary_followee")
	private GenericItem<String> beneficiaryFollowee;
		
	@JsonProperty("follow_date")
	private long followDate;
	
	public BeneficiaryFollowItem() {
		super();
	}	

	public BeneficiaryFollowItem(GenericItem<String> userFollower, GenericItem<String> beneficiaryFollowee, long followDate) {
		super();
		this.userFollower = userFollower;
		this.beneficiaryFollowee = beneficiaryFollowee;
		this.followDate = followDate;
	}

}