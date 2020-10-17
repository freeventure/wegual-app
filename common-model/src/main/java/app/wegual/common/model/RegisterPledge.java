package app.wegual.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterPledge {	
	private String userId;
	private String beneficiaryId;
	private String giveupId;
	private String currency;
	private String amount;
	private String description;
	private String baseAmount;
	private String baseCurrency;
}