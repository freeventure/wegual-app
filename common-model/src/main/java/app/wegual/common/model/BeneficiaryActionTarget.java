package app.wegual.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeneficiaryActionTarget implements ActionTarget<String, BeneficiaryActionTargetType>{
	
	private static final long serialVersionUID = -4555502540905948391L;
	private BeneficiaryActionTargetType actionTargetType;
	private String id;
	private String name;
	private String summary;
	private String permalink;
	
	public BeneficiaryActionTarget() {
		
	}
	
	public BeneficiaryActionTarget(BeneficiaryActionTargetType actionTargetType, String id, String name, String summary,
			String permalink) {
		super();
		this.actionTargetType = actionTargetType;
		this.id = id;
		this.name = name;
		this.summary = summary;
		this.permalink = permalink;
	}

}