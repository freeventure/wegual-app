package app.wegual.common.client.oauth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractRestClientConfig {

	private String clientId;
	private String clientSecret;
	private String scope;
	private String grantType;
	private String accessTokenUri;
	
	public abstract String getServiceId();
	
	public ClientCredentialsResourceDetails fromMe() {
		ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
		details.setClientId(clientId);
		details.setClientSecret(clientSecret);
		details.setScope(getScopes());
		details.setGrantType(grantType);
		details.setAccessTokenUri(accessTokenUri);
		
		return details;
	}
	
	private List<String> getScopes() {
		if(!StringUtils.isEmpty(scope))
		{
			List<String> scopes = Arrays.asList(scope.split(" ")); 
			return scopes;
		}
		return new ArrayList<String>();
	}
	

	public AbstractRestClientConfig() {
		super();
	}

//	public String getClientId() {
//		return clientId;
//	}

//	public void setClientId(String clientId) {
//		this.clientId = clientId;
//	}
//
//	public String getClientSecret() {
//		return clientSecret;
//	}
//
//	public void setClientSecret(String clientSecret) {
//		this.clientSecret = clientSecret;
//	}
//
//	public String getScope() {
//		return scope;
//	}
//
//	public void setScope(String scope) {
//		this.scope = scope;
//	}

//	public String getGrantType() {
//		return grantType;
//	}
//
//	public void setGrantType(String grantType) {
//		this.grantType = grantType;
//	}
	
	@PostConstruct
	public void register() {
		RestClientConfigFactory.getInstance().register(getServiceId(), this);
	}

	
}