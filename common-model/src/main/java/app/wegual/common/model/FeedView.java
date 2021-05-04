package app.wegual.common.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedView implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String userId;
	private String postId;
	private long viewDate;
	
	public FeedView(String id, String userId, String postId, long viewDate) {
		super();
		this.id = id;
		this.userId = userId;
		this.postId = postId;
		this.viewDate = viewDate;
	}

	public FeedView() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
