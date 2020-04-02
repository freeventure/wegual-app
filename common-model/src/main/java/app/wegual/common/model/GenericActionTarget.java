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
}
