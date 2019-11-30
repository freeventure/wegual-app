package app.wegual.poc.rest.model;

import java.util.ArrayList;
import java.util.List;

public class BeneficiaryFollowers {
	
	Long beneficiaryId;
	Long followersCount;
	
	public BeneficiaryFollowers() {
		
	}
	
	public BeneficiaryFollowers(Long beneficiaryId, Long followersCount) {
		super();
		this.beneficiaryId = beneficiaryId;
		this.followersCount = followersCount;
	}


	public Long getBeneficiaryId() {
		return beneficiaryId;
	}
	public void setBeneficiaryId(Long beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}
	public Long getFollowersCount() {
		return followersCount;
	}
	public void setFollowersCount(Long followersCount) {
		this.followersCount = followersCount;
	}
	
	public static BeneficiaryFollowers sample() {
		return new  BeneficiaryFollowers(new Long(14), new Long(2));
	}

	public static List<BeneficiaryFollowers> samplePopular() {
		
		List<BeneficiaryFollowers> popular = new ArrayList<>();
		popular.add(new  BeneficiaryFollowers(new Long(14), new Long(22)));
		popular.add(new  BeneficiaryFollowers(new Long(53), new Long(18)));
		popular.add(new  BeneficiaryFollowers(new Long(58), new Long(10)));
		return popular;
	}

}
