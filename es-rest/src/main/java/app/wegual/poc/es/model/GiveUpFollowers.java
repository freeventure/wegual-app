package app.wegual.poc.es.model;

import org.springframework.stereotype.Component;

@Component
public class GiveUpFollowers {
	
	private String userFollowerId;
	private String giveUpFolloweeId;
	private String userFollowerName;
	private String giveUpFolloweeName;
	
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
	public String getUserFollowerName() {
		return userFollowerName;
	}
	public void setUserFollowerName(String userFollowerName) {
		this.userFollowerName = userFollowerName;
	}
	public String getGiveUpFolloweeName() {
		return giveUpFolloweeName;
	}
	public void setGiveUpFolloweeName(String giveUpFolloweeName) {
		this.giveUpFolloweeName = giveUpFolloweeName;
	}
	
	
}
