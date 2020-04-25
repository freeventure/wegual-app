package app.wegual.common.rest.model;

import java.util.ArrayList;
import java.util.List;

public class BeneficiaryFollowers {
	
	String beneficiaryId;
	Long followersCount;
	
	public BeneficiaryFollowers() {
		
	}
	
	public BeneficiaryFollowers(String beneficiaryId, Long followersCount) {
		super();
		this.beneficiaryId = beneficiaryId;
		this.followersCount = followersCount;
	}


	public String getBeneficiaryId() {
		return beneficiaryId;
	}
	public void setBeneficiaryId(String beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}
	public Long getFollowersCount() {
		return followersCount;
	}
	public void setFollowersCount(Long followersCount) {
		this.followersCount = followersCount;
	}
	
	public static BeneficiaryFollowers sample() {
		return new  BeneficiaryFollowers("14", new Long(2));
	}

	public static List<BeneficiaryFollowers> samplePopular() {
		
		List<BeneficiaryFollowers> popular = new ArrayList<>();
		popular.add(new  BeneficiaryFollowers("14", new Long(22)));
		popular.add(new  BeneficiaryFollowers("53", new Long(18)));
		popular.add(new  BeneficiaryFollowers("58", new Long(10)));
		return popular;
	}

}
