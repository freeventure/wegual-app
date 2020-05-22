package app.wegual.common.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GiveUpLike implements Serializable{
	
	private static final long serialVersionUID = 6699095014694712851L;

	private String id;

	private GenericItem<String> user;
	
	private GenericItem<String> giveup;
	
	@JsonProperty("like_date")
	private long likeDate;

}
