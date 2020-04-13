package app.wegual.common.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Currency;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;


public class Pledge implements Serializable {
    private Long id;

	private User pledgedBy;
    
	private Timestamp pledgedDate;
    
	private Double amount;
    
	private Currency currency;
	
	private Beneficiary beneficiary;
    
	private GiveUp giveUp;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getPledgedBy() {
		return pledgedBy;
	}
	public void setPledgedBy(User pledgedBy) {
		this.pledgedBy = pledgedBy;
	}
	public Timestamp getPledgedDate() {
		return pledgedDate;
	}
	public void setPledgedDate(Timestamp pledgedDate) {
		this.pledgedDate = pledgedDate;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public Beneficiary getBeneficiary() {
		return beneficiary;
	}
	public void setBeneficiary(Beneficiary beneficiary) {
		this.beneficiary = beneficiary;
	}
	public GiveUp getGiveUp() {
		return giveUp;
	}
	public void setGiveUp(GiveUp giveUp) {
		this.giveUp = giveUp;
	}
	
	
}
