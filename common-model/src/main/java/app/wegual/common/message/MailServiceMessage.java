package app.wegual.common.message;

import app.wegual.common.model.UserActionTargetType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailServiceMessage extends AbstractMessageObject<Long> {

	private static final long serialVersionUID = -2379467793470635779L;
	
	public MailServiceMessage() {
		
	}
	
	public MailServiceMessage(Long id, String name) {
		this.id = id;
		this.setName(name);
	}

	public String getPermalink() {
		return "/user/" + getId();
	}

	public UserActionTargetType getActionType() {
		return UserActionTargetType.USER;
	}

}
