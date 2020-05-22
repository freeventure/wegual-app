package app.wegual.common.model;

import java.io.Serializable;
import java.util.Currency;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Pledge implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	private String id;
    
    @JsonProperty("pledged_by")
    private GenericItem pledgedBy;
    
    private GenericItem beneficiary;
    
    @JsonProperty("give_up")
	private GenericItem giveUp;

    @JsonProperty("pledged_date")
	private Long pledgedDate;
    
	private Double amount;
    
	private Currency currency;
	
}