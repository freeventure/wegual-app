package app.wegual.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericActionTarget<T, ATT> implements ActionTarget<T, ATT> {

	private static final long serialVersionUID = -4555502540905948391L;
	private ATT actionTargetType;
	private T id;
	private String name;
	private String summary;
	private String permalink;
	
	public GenericActionTarget() {
		
	}
	
	public GenericActionTarget(ATT actionTargetType, T id, String name, String summary,
			String permalink) {
		super();
		this.actionTargetType = actionTargetType;
		this.id = id;
		this.name = name;
		this.summary = summary;
		this.permalink = permalink;
	}
	
}
