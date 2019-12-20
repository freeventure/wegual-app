package app.wegual.poc.es.model;

import org.springframework.stereotype.Component;

@Component
public class BeneficiaryFollowers {
	
	private String beneficiaryFolloweeId;
	private String userFollowerId;
	
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
}
