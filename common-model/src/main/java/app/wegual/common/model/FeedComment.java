package app.wegual.common.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedComment implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("post_id")
	private String postId;
	@JsonProperty("comment_date")
	private long comment_date;
	@JsonProperty("comment")
	private String comment;
	@JsonProperty("commenter")
	private GenericItem<String> commenter;

	public FeedComment(String postId, long comment_date, String comment, GenericItem<String> commenter) {
		super();
		this.postId = postId;
		this.comment_date = comment_date;
		this.comment = comment;
		this.commenter = commenter;
	}

	public FeedComment() {
		super();
	}
	
	
}
