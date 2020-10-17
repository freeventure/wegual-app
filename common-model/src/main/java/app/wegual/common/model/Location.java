package app.wegual.common.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location implements Serializable{
	
	private static final long serialVersionUID = -8518656206472579860L;
	
	private String country;
	private String state;
	private String city;
}