package com.wegual.pledgeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PledgeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PledgeServiceApplication.class, args);
	}

}
