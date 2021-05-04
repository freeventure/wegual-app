package app.wegual.common.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedItem<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
	protected String id;
	
	protected GenericItem<T> actor;
	
	protected String detail;
	
	@JsonProperty("detail_actions")
	protected FeedItemDetailActions detailActions;
	
	@JsonProperty("date_stamp")
	protected Long actionDate;
	
	@JsonProperty("verb")
	protected UserActionType userActionType;

	@JsonProperty("target_object")
	protected ActionTarget<T> target;
	
	@JsonProperty("action_object")
	protected ActionTarget<T> actionObject;

	public FeedItem(GenericItem<T> actor, String detail, FeedItemDetailActions detailActions,
			ActionTarget<T> actionObject) {
		this.actor = actor;
		this.detail = detail;
		this.detailActions = detailActions;
		this.actionObject = actionObject;
	}

	public FeedItem() {
		super();
	}
	
	
}
