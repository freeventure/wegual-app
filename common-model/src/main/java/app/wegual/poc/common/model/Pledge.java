package app.wegual.poc.common.model;

import java.io.Serializable;
import java.util.Currency;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Pledge implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name="PLEDGED_BY", nullable=false, insertable=true, updatable=false)
	private User pledgedBy;
    
    @Column(nullable=false)
	private Date pledgedDate;
    
    @Column(nullable=false)
	private Double amount;
    
    @Column(nullable=false)
	private Currency currency;
	
    @OneToOne
    @JoinColumn(name="BENEFICIARY_ID", nullable=false, insertable=true, updatable=false)
	private Beneficiary beneficiary;
    
    @OneToOne
    @JoinColumn(name="GIVE_UP_ID", nullable=true, insertable=true, updatable=true)
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
	public Date getPledgedDate() {
		return pledgedDate;
	}
	public void setPledgedDate(Date pledgedDate) {
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
