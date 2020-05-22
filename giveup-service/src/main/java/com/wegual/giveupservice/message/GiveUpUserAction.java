package com.wegual.giveupservice.message;

import java.io.Serializable;

import app.wegual.common.model.GenericItem;
import app.wegual.common.model.UserActionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GiveUpUserAction implements Serializable {
	private static final long serialVersionUID = 1L;
	private GenericItem<Long> giveUp;
	private GenericItem<String> user;
	private UserActionType userActionType;
	private long actionDate;
	
	public GiveUpUserAction() {
		
	}
	
	public GiveUpUserAction(GenericItem<Long> gu, GenericItem<String> u) {
		this.giveUp = gu;
		this.user = u;
		actionDate = System.currentTimeMillis();
	}

}
