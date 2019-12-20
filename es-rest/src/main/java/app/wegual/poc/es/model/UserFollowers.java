package app.wegual.poc.es.model;

import org.springframework.stereotype.Component;

@Component
public class UserFollowers {
	
	private String userFollowerId;
	private String userFolloweeId;
	
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
	
		
}
