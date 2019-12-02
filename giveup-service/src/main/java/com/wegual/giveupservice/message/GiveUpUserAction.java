package com.wegual.giveupservice.message;

import java.io.Serializable;
import java.util.Date;

import app.wegual.common.model.GiveUp;
import app.wegual.common.model.User;
import app.wegual.common.model.UserActionType;


public class GiveUpUserAction implements Serializable {
	private static final long serialVersionUID = 1L;
	private GiveUp giveUp;
	private User user;
	private UserActionType userActionType;
	private Date actionDate;
	
	public GiveUpUserAction() {
		
	}
	
	public GiveUpUserAction(GiveUp gu, User u) {
		giveUp = gu;
		user = u;
		actionDate = new Date();
	}
	
	public GiveUp getGiveUp() {
		return giveUp;
	}
	public void setGiveUp(GiveUp gu) {
		this.giveUp = gu;
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
