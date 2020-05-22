package app.wegual.common.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserActionItem implements Serializable{

	String actorId;
	String targetId;
	UserActionType actionType;
}
