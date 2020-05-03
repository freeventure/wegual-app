package app.wegual.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import app.wegual.common.rest.model.BeneficiarySnapshot;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeneficiaryProfileData {
	private Beneficiary beneficiary;
	private BeneficiarySnapshot beneficiarySnapshot;

}
