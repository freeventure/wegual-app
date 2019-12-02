package com.wegual.giveupservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableAutoConfiguration
@EntityScan(basePackages = "app.wegual.common.model")
public class GiveupServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GiveupServiceApplication.class, args);
	}

}
