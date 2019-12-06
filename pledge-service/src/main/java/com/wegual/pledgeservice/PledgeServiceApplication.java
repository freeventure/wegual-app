package com.wegual.pledgeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableAutoConfiguration
public class PledgeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PledgeServiceApplication.class, args);
	}

}
