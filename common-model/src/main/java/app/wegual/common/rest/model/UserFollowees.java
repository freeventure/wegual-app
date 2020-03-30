package app.wegual.common.rest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFollowees {

	String userId;
	Long followeesCount;

	public UserFollowees() {

	}

	public UserFollowees(String userId, Long followeesCount) {
		this.userId = userId;
		this.followeesCount = followeesCount;
	}

}
