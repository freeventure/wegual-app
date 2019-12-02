package app.wegual.common.rest.model;

import java.util.ArrayList;
import java.util.List;

public class GiveUpFollowers {
	
	Long giveUpId;
	Long followersCount;
	
	public GiveUpFollowers() {
		
	}
	
	public GiveUpFollowers(Long giveUpId, Long followersCount) {
		super();
		this.giveUpId = giveUpId;
		this.followersCount = followersCount;
	}


	public Long getGiveUpId() {
		return giveUpId;
	}
	public void setGiveUpId(Long giveUpId) {
		this.giveUpId = giveUpId;
	}
	public Long getFollowersCount() {
		return followersCount;
	}
	public void setFollowersCount(Long followersCount) {
		this.followersCount = followersCount;
	}
	
	public static GiveUpFollowers sample() {
		return new  GiveUpFollowers(new Long(14), new Long(2));
	}

	public static List<GiveUpFollowers> samplePopular() {
		
		List<GiveUpFollowers> popular = new ArrayList<>();
		popular.add(new  GiveUpFollowers(new Long(14), new Long(22)));
		popular.add(new  GiveUpFollowers(new Long(53), new Long(18)));
		popular.add(new  GiveUpFollowers(new Long(58), new Long(10)));
		return popular;
	}

}
