package app.wegual.common.model;

import java.io.Serializable;

public class ObjectCreate implements Serializable {

	private String name;
	private String creator;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	
}
