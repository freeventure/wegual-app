package com.wegual.webapp.ui.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyCode {

	private String token;
	private String tokenId;
	private String tokenEntity; 
}
