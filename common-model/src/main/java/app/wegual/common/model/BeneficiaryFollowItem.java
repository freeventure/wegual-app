package app.wegual.common.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeneficiaryFollowItem{

	@JsonProperty("user_follower")
	private GenericItem<String> userFollower;
		
	@JsonProperty("beneficiary_followee")
	private GenericItem<Long> beneficiaryFollowee;
		
	@JsonProperty("follow_date")
	private Timestamp followDate;
	
	public BeneficiaryFollowItem() {
		super();
	}	

	public BeneficiaryFollowItem(GenericItem<String> userFollower, GenericItem<Long> beneficiaryFollowee,
			Timestamp followDate) {
		super();
		this.userFollower = userFollower;
		this.beneficiaryFollowee = beneficiaryFollowee;
		this.followDate = followDate;
	}

}
