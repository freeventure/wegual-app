package app.wegual.common.rest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFollowers {

	String userId;
	Long followersCount;

	public UserFollowers() {

	}

	public UserFollowers(String userId, Long followersCount) {
		this.userId = userId;
		this.followersCount = followersCount;
	}

}
