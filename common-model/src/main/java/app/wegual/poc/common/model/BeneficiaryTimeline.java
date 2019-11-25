package app.wegual.poc.common.model;

import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class BeneficiaryTimeline implements Serializable{
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@Column
	private String beneficiaryId;
	
	@Column
	private String operationType;
	
	@Column
	private Timestamp timestamp;
	
	public BeneficiaryTimeline withId(String id) {
		this.beneficiaryId = id;
		return this;
	}
	
	public BeneficiaryTimeline withOperationType(String op) {
		this.operationType = op;
		return this;
	}
	
	public BeneficiaryTimeline withTimestamp(Timestamp ts) {
		this.timestamp = ts;
		return this;
	}
	
	public String getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(String beneficiaryId) {
		this.beneficiaryId = "ben" + beneficiaryId;
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
