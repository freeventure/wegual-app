package app.wegual.common.rest.model;

import java.util.Map;

public class BeneficiarySnapshot {

	private Long beneficiaryId;
	private Long userCount;
	private Long giveUpCount;
	private Long pledgesCount;
	private Double totalPledged;
	private Map<String, Double> amountByCurrency;
	
	public BeneficiarySnapshot withBeneficiaryId(Long beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
		return this;
	}
	
	public Long getBeneficiaryId() {
		return beneficiaryId;
	}
	public void setBeneficiaryId(Long beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}
	public Long getUserCount() {
		return userCount;
	}
	public void setUserCount(Long userCount) {
		this.userCount = userCount;
	}
	public Long getGiveUpCount() {
		return giveUpCount;
	}
	public void setGiveUpCount(Long giveUpCount) {
		this.giveUpCount = giveUpCount;
	}
	
	public Long getPledgesCount() {
		return pledgesCount;
	}

	public void setPledgesCount(Long pledgesCount) {
		this.pledgesCount = pledgesCount;
	}

	public Double getTotalPledged() {
		return totalPledged;
	}
	public void setTotalPledged(Double totalPledged) {
		this.totalPledged = totalPledged;
	}
	public Map<String, Double> getAmountByCurrency() {
		return amountByCurrency;
	}
	public void setAmountByCurrency(Map<String, Double> amountByCurrency) {
		this.amountByCurrency = amountByCurrency;
	}
	
	public static BeneficiarySnapshot sample() {
		return new BeneficiarySnapshot();
	}
	
}
