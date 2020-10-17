package app.wegual.common.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedItemDetailActions implements Serializable{
	private long likes;
	private long shares;
	private long comment;

	public FeedItemDetailActions() {
		super();
	}

	public FeedItemDetailActions(long likes, long shares, long comment) {
		super();
		this.likes = likes;
		this.shares = shares;
		this.comment = comment;
	}
	
}
