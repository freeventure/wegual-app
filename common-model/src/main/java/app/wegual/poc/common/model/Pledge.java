package app.wegual.poc.common.model;

import java.util.Currency;
import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Pledge {

	private String id;
	private String userId;
	private Date pledgedDate;
	private double amount;
	private Currency currency;
	private String beneficiaryId;
	private String giveUpId;

	public String getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getPledgedDate() {
		return pledgedDate;
	}

	public void setPledgedDate(Date pledgedDate) {
		this.pledgedDate = pledgedDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(String beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public String getGiveUpId() {
		return giveUpId;
	}

	public void setGiveUpId(String giveUpId) {
		this.giveUpId = giveUpId;
	}
}
