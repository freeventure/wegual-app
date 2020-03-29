package app.wegual.poc.scheduler.clients.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "user-service")
public class UserServiceRestClientConfig extends AbstractRestClientConfig {

	@Override
	public String getServiceId() {
		return "user-service";
	}
}