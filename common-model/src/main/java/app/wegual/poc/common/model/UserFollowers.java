package app.wegual.poc.common.model;

import javax.persistence.Entity;

@Entity
public class UserFollowers {
	
	private String userFollowerId;
	private String userFolloweeId;
	private String userFollowerName;
	private String userFolloweeName;
	
	public String getUserFollowerId() {
		return userFollowerId;
	}
	public void setUserFollowerId(String userFollowerId) {
		this.userFollowerId = userFollowerId;
	}
	public String getUserFolloweeId() {
		return userFolloweeId;
	}
	public void setUserFolloweeId(String userFolloweeId) {
		this.userFolloweeId = userFolloweeId;
	}
	public String getUserFollowerName() {
		return userFollowerName;
	}
	public void setUserFollowerName(String userFollowerName) {
		this.userFollowerName = userFollowerName;
	}
	public String getUserFolloweeName() {
		return userFolloweeName;
	}
	public void setUserFolloweeName(String userFolloweeName) {
		this.userFolloweeName = userFolloweeName;
	}
	
		
}
