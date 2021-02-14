package app.wegual.common.rest.model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeneficiarySnapshot {

	private String beneficiaryId;
	private Long userCount;
	private Long giveUpCount;
	private Long pledgesCount;
	private Double totalPledged;
	private Map<String, Double> amountByCurrency;
	
	public static BeneficiarySnapshot sample() {
		return new BeneficiarySnapshot();
	}
	
	public BeneficiarySnapshot withBeneficiaryId(String beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
		return this;
	}
	
	public BeneficiarySnapshot(String beneficiaryId, Long userCount, Long giveUpCount, Long pledgesCount,
			Double totalPledged, Map<String, Double> amountByCurrency) {
		super();
		this.beneficiaryId = beneficiaryId;
		this.userCount = userCount;
		this.giveUpCount = giveUpCount;
		this.pledgesCount = pledgesCount;
		this.totalPledged = totalPledged;
		this.amountByCurrency = amountByCurrency;
	}

	public BeneficiarySnapshot() {
		super();
	}
	
}
