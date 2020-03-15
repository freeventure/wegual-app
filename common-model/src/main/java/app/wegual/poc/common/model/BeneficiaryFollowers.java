package app.wegual.poc.common.model;

import javax.persistence.Entity;

@Entity
public class BeneficiaryFollowers {
	
	private String beneficiaryFolloweeId;
	private String userFollowerId;
	private String beneficiaryFolloweeName;
	private String userFollowerName;
	
	public String getBeneficiaryFolloweeId() {
		return beneficiaryFolloweeId;
	}
	public void setBeneficiaryFolloweeId(String beneficiaryFolloweeId) {
		this.beneficiaryFolloweeId = beneficiaryFolloweeId;
	}
	public String getUserFollowerId() {
		return userFollowerId;
	}
	public void setUserFollowerId(String userFollowerId) {
		this.userFollowerId = userFollowerId;
	}
	public String getBeneficiaryFolloweeName() {
		return beneficiaryFolloweeName;
	}
	public void setBeneficiaryFolloweeName(String beneficiaryFolloweeName) {
		this.beneficiaryFolloweeName = beneficiaryFolloweeName;
	}
	public String getUserFollowerName() {
		return userFollowerName;
	}
	public void setUserFollowerName(String userFollowerName) {
		this.userFollowerName = userFollowerName;
	}
	
}
