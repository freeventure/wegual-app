package app.wegual.common.message;

import app.wegual.common.model.ActionTarget;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractMessageObject<T, AT> implements ActionTarget<T, AT> {
	private static final long serialVersionUID = -2379467793470635779L;
	protected T id;
	protected String permalink;
	protected String type;
	private String summary;
	private String name;
	
}
