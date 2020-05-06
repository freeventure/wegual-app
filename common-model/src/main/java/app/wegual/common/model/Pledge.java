package app.wegual.common.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Currency;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Pledge implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
    
    @JsonProperty("pledged_by")
    private GenericItem<String> pledgedBy;
    
    private GenericItem<Long> beneficiary;
    
    @JsonProperty("give_up")
	private GenericItem<Long> giveUp;

    @JsonProperty("pledged_date")
	private Timestamp pledgedDate;
    
	private Double amount;
    
	private Currency currency;
	
}
