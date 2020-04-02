package app.wegual.common.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo (use = JsonTypeInfo.Id.CLASS, include = As.PROPERTY, property = "classNameExtenral")
@JsonSubTypes ({@Type (value = GenericActionTarget.class, name = "genericactiontarget")})
public interface ActionTarget<T> extends Serializable {

	UserActionTargetType getActionType();
	String getPermalink();
	
	T getId();
	String getSummary();
	String getName();
}
