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

	@JsonProperty("pledge_id")
	private String id;
    
    @JsonProperty("pledged_by")
    private GenericItem<String> pledgedBy;
    
    private GenericItem<String> beneficiary;
    
    @JsonProperty("give_up")
	private GenericItem<String> giveUp;

    @JsonProperty("pledged_date")
	private Long pledgedDate;
    
	private Double amount;
    
	private Currency currency;
	
	private String description;
	
	@JsonProperty("base_currency_amount")
	private double baseCurrencyAmount;

	@JsonProperty("base_currency")
	private Currency baseCurrency;
}
