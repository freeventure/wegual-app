package com.wegual.webapp;

import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(
		scanBasePackages={"app.wegual.common.client", "app.wegual.common.repository", "app.wegual.common.service", "app.wegual.common.model", "com.wegual.webapp"})
@EnableJpaRepositories(basePackages = {"app.wegual.common.repository"})
@EntityScan(basePackages =  {"app.wegual.common.model"})
@EnableConfigurationProperties(KeycloakSpringBootProperties.class)
public class WebappApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);
	}
	
}
