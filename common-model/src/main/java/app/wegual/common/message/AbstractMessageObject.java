package app.wegual.common.message;

import app.wegual.common.model.ActionTarget;

public abstract class AbstractMessageObject<T> implements ActionTarget<T> {
	private static final long serialVersionUID = -2379467793470635779L;
	protected T id;
	protected String permalink;
	protected String type;
	private String summary;
	private String name;
	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public T getId() {
		return id;
	}
	public void setId(T id) {
		this.id = id;
	}
	
}
