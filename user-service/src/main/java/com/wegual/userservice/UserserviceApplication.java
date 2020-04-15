package com.wegual.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDiscoveryClient
@SpringBootApplication(
		scanBasePackages={"app.wegual.common.client", "app.wegual.common.service", "com.wegual.userservice"},
		exclude = {SecurityAutoConfiguration.class})
@EnableJpaRepositories(basePackages = {"app.wegual.common.repository"})
@EntityScan(basePackages =  {"app.wegual.common.model"})
public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

}
