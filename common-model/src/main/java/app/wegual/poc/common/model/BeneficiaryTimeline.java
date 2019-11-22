package app.wegual.poc.common.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;

import org.hibernate.annotations.CreationTimestamp;

public class BeneficiaryTimeline implements Serializable {
	
	@Column
	private String beneficiaryId;
	
	@Column
	private String operationType;
	
	@CreationTimestamp
	private Timestamp timestamp;
	
	public String getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(String beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
