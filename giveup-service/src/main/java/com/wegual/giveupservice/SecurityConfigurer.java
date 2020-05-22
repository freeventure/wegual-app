package com.wegual.giveupservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({ GiveUpSecurityProperties.class })
public class SecurityConfigurer extends ResourceServerConfigurerAdapter{
	
	@Autowired
	private ResourceServerProperties resourceServerProperties;

	@Autowired
	private GiveUpSecurityProperties giveUpSecurityProperties;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(resourceServerProperties.getResourceId());
	}
	
	@Override
	public void configure(final HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/test/**").permitAll()
		.antMatchers("/giveup/public/**").permitAll();

		http.cors().configurationSource(corsConfigurationSource()).and().headers().frameOptions().disable().and().csrf()
				.disable().authorizeRequests().antMatchers(giveUpSecurityProperties.getApiMatcher()).authenticated();

	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		if (null != giveUpSecurityProperties.getCorsConfiguration()) {
			source.registerCorsConfiguration("/**", giveUpSecurityProperties.getCorsConfiguration());
		}
		return source;
	}

}