package app.wegual.common.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedLike implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String userId;
	private String postId;
	private long likeDate;
	
	public FeedLike(String id, String userId, String postId, long likeDate) {
		super();
		this.id = id;
		this.userId = userId;
		this.postId = postId;
		this.likeDate = likeDate;
	}

	public FeedLike() {
		super();
	}
	
	
	
}
