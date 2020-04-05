package app.wegual.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericActionTarget implements ActionTarget<String> {

	private static final long serialVersionUID = -4555502540905948391L;
	private UserActionTargetType actionType;
	private String id;
	private String name;
	private String summary;
	private String permalink;
	
	public GenericActionTarget() {
		
	}
	
	public GenericActionTarget(UserActionTargetType actionType, String id, String name, String summary,
			String permalink) {
		super();
		this.actionType = actionType;
		this.id = id;
		this.name = name;
		this.summary = summary;
		this.permalink = permalink;
	}
	
	
}
