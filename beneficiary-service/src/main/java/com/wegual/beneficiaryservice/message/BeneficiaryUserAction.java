package com.wegual.beneficiaryservice.message;

import java.io.Serializable;
import java.util.Date;

import app.wegual.common.model.Beneficiary;
import app.wegual.common.model.User;
import app.wegual.common.model.UserActionType;


public class BeneficiaryUserAction implements Serializable {
	private static final long serialVersionUID = 1L;
	private Beneficiary beneficiary;
	private User user;
	private UserActionType userActionType;
	private Date actionDate;
	
	public BeneficiaryUserAction() {
		
	}
	
	public BeneficiaryUserAction(Beneficiary b, User u) {
		beneficiary = b;
		user = u;
		actionDate = new Date();
	}
	
	public Beneficiary getBeneficiary() {
		return beneficiary;
	}
	public void setBeneficiary(Beneficiary beneficiary) {
		this.beneficiary = beneficiary;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public UserActionType getUserActionType() {
		return userActionType;
	}
	public void setUserActionType(UserActionType userActionType) {
		this.userActionType = userActionType;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

}
