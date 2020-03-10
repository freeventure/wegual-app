package app.wegual.common.message;

public class MailServiceMessage extends AbstractMessageObject {

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
}
