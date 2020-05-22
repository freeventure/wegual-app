package app.wegual.common.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericItem implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String permalink;
	
	@JsonProperty("picture_link")
	private String pictureLink;
	
	public GenericItem() {
		super();
	}
	
	public GenericItem(String id, String name, String permalink, String pictureLink) {
		super();
		this.id = id;
		this.name = name;
		this.permalink = permalink;
		this.pictureLink = pictureLink;
	}

}
