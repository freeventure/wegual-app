package app.wegual.poc.common.model;

import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;

import javax.persistence.Column;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeneficiaryTimeline{
	
	@Column
	private String beneficiaryId;
	
	@Column
	private String operationType;
	
	@Column
	private Timestamp timestamp;
	
	public BeneficiaryTimeline(@JsonProperty("beneficiaryId") String beneficiaryId,
								@JsonProperty("opertionType") String operationType) {
		this.beneficiaryId = beneficiaryId;
		this.operationType = operationType;
		this.timestamp = new Timestamp(new Date().getTime());
	}
	
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
	
	@Override
	public String toString() {
        return "BeneficiaryTimeline{" +
                "benId='" + beneficiaryId + "'"+
                ", operationType='" + operationType +"'"+
                ", timestamp=" + timestamp +
                '}';
	}
}
