package com.wegual.mailservice.mail;

import java.util.HashMap;

public class MailParameters {

	HashMap<String, String> params;
	
	public MailParameters(HashMap<String, String> mailParams) {
		params = mailParams;
	}
	
	public void validate() {
		if(params == null || params.isEmpty())
			throw new IllegalArgumentException("Mail service invoked without any parameters");
		String recipient = params.get("recipient");
		String template = params.get("template");
		if(recipient == null || recipient.isEmpty())
			throw new IllegalArgumentException("Mail service missing recipient");
		if(template == null || template.isEmpty())
			throw new IllegalArgumentException("Mail service missing template");
	}
	
	public String from() {
		return params.get("from");
	}

	public String recipient() {
		return params.get("recipient");
	}

	public String template() {
		return params.get("template");
	}

	public String subject() {
		return params.get("subject");
	}
	
}
