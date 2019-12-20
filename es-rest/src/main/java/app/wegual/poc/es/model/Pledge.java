package app.wegual.poc.es.model;

import java.util.Currency;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class Pledge {
	
	private String user_id;
	private Date pledgedDate;
	private double amount;
	private Currency currency;
	private String beneficiary_id;
	private String giveUp_id;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
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
	public String getBeneficiary_id() {
		return beneficiary_id;
	}
	public void setBeneficiary_id(String beneficiary_id) {
		this.beneficiary_id = beneficiary_id;
	}
	public String getGiveUp_id() {
		return giveUp_id;
	}
	public void setGiveUp_id(String giveUp_id) {
		this.giveUp_id = giveUp_id;
	}
}
