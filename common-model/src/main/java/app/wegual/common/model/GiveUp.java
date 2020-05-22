package app.wegual.common.model;

import java.io.Serializable;
//import java.security.Timestamp;
import java.sql.Timestamp;
import java.util.Currency;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GiveUp implements Serializable {
	
	@JsonProperty("giveup_id")
    private String id;
	
	@JsonProperty("giveup_name")
	private String name;
	
	private String description;
	
	@JsonProperty("created_by")
	private GenericItem createdBy;
	
	@JsonProperty("created_date")
	private long creationDate;
    
	@JsonProperty("updation_date")
   	private long updationDate;

}