package app.wegual.common.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimelineItemDetailActions implements Serializable{
	private boolean viewDetail;
	private boolean share;
	
	public TimelineItemDetailActions() {
		
	}
	public TimelineItemDetailActions(boolean viewDetail, boolean share) {
		this.viewDetail = viewDetail;
		this.share = share;
	}
}
