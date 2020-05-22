package app.wegual.common.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GiveUp implements Serializable {
	
	private static final long serialVersionUID = -6776073275796738101L;

	@JsonProperty("giveup_id")
    private String id;
	
	@JsonProperty("giveup_name")
	private String name;
	
	private String description;
	
	@JsonProperty("created_by")
	private GenericItem<String> createdBy;
	
	@JsonProperty("picture_link")
	private String pictureLink;
	
	@JsonProperty("created_date")
	private long creationDate;
    
	@JsonProperty("updation_date")
   	private long updationDate;
	
	public GiveUp() {
		
	}
	
	public GiveUp(String id, String name, String description, GenericItem<String> createdBy, String pictureLink, long creationDate, long updationDate) {
		this.id = id;
		this.description = description;
		this.createdBy = createdBy;
		this.pictureLink = pictureLink;
		this.creationDate = creationDate;
		this.updationDate = updationDate;
	}

}