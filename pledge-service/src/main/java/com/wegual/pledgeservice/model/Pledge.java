package com.wegual.pledgeservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pledge {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;

	String name;
	
}
