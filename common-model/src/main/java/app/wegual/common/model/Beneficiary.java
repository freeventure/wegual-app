package app.wegual.common.model;

import java.io.Serializable;
import java.util.Currency;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Beneficiary  implements Serializable {
	private static final long serialVersionUID = -867409585306111160L;

	@JsonProperty("beneficiary_id")
	private String id;

	@JsonProperty("beneficiary_name")
	private String name;

	private String description;

	@JsonProperty("beneficiary_type")
	private BeneficiaryType beneficiaryType;

	private String email;

	@JsonIgnore
	private String url;

	@JsonIgnore
	@JsonProperty("facebook_page")
	private String facebookPage;

	@JsonIgnore
	@JsonProperty("twitter_handle")
	private String twitterHandle;

	@JsonIgnore
	@JsonProperty("linkedin_page")
	private String linkedInPage;

	@JsonProperty("owner_id")
	private String ownerId;

	@JsonProperty("created_date")
	private Long createdDate;

	@JsonProperty("updated_date")
	private Long updatedDate;

	@JsonProperty("picture_link")
	private String pictureLink;

	private Location location;

	@JsonProperty("base_currency")
	private Currency baseCurrency;

}