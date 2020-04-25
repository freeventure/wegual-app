package app.wegual.common.message;

import app.wegual.common.model.UserActionTargetType;

public class MailServiceMessage extends AbstractMessageObject<Long, UserActionTargetType> {

	private static final long serialVersionUID = -2379467793470635779L;
	
	public MailServiceMessage() {
		
	}
	
	public MailServiceMessage(Long id, String name) {
		this.setId(id);
		this.setName(name);
	}
	
	public String getPermalink() {
		return "/user/" + getId();
	}

	public String getType() {
		return "User";
	}

	@Override
	public UserActionTargetType getActionTargetType() {
		// TODO Auto-generated method stub
		return null;
	}

}
