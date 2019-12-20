package app.wegual.poc.es.model;

import org.springframework.stereotype.Component;

@Component
public class GiveUpFollowers {
	
	private String userFollowerId;
	private String giveUpFolloweeId;
	
	public String getUserFollowerId() {
		return userFollowerId;
	}
	public void setUserFollowerId(String userFollowerId) {
		this.userFollowerId = userFollowerId;
	}
	public String getGiveUpFolloweeId() {
		return giveUpFolloweeId;
	}
	public void setGiveUpFolloweeId(String giveUpFolloweeId) {
		this.giveUpFolloweeId = giveUpFolloweeId;
	}
	
	
}
