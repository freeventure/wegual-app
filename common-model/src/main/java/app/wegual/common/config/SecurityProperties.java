package app.wegual.common.config;

import java.util.List;

import org.springframework.web.cors.CorsConfiguration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class SecurityProperties {
	
	protected boolean enabled;
	protected String apiMatcher;
	protected Cors cors;
	protected String issuerUri;

	public CorsConfiguration getCorsConfiguration() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(cors.getAllowedOrigins());
		corsConfiguration.setAllowedMethods(cors.getAllowedMethods());
		corsConfiguration.setAllowedHeaders(cors.getAllowedHeaders());
		corsConfiguration.setExposedHeaders(cors.getExposedHeaders());
		corsConfiguration.setAllowCredentials(cors.getAllowCredentials());
		corsConfiguration.setMaxAge(cors.getMaxAge());

		return corsConfiguration;
	}
	
	@Getter
	@Setter
	public static class Cors {

		private List<String> allowedOrigins;
		private List<String> allowedMethods;
		private List<String> allowedHeaders;
		private List<String> exposedHeaders;
		private Boolean allowCredentials;
		private Long maxAge;
	

	}

}
