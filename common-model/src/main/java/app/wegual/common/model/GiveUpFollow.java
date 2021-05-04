package app.wegual.common.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GiveUpFollow implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String id;

	@JsonProperty("user_follower")
	private GenericItem<String> userFollower;
	
	@JsonProperty("giveup_followee")
	private GenericItem<String> giveupFollowee;
	
	@JsonProperty("follow_date")
	private long followDate;
}
