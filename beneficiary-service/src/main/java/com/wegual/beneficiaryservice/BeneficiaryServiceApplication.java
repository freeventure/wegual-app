package com.wegual.beneficiaryservice;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import app.wegual.common.util.MessagingConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableDiscoveryClient
@SpringBootApplication(
		scanBasePackages={"app.wegual.common.client", "app.wegual.common.service", "com.wegual.beneficiaryservice"},
		exclude = {SecurityAutoConfiguration.class})
@EnableJpaRepositories(basePackages = {"app.wegual.common.repository"})
@EntityScan(basePackages =  {"app.wegual.common.model"})
public class BeneficiaryServiceApplication {

	private static final String fanoutExchangeName = MessagingConstants.BENEFICIARY_FANOUT_EXCHANGE_NAME;
	private static final String queueNameBeneficiaryTimeline = MessagingConstants.BENEFICIARY_TIMELINE_QUEUE_NAME;
	private static final String queueNameBeneficiaryActions = MessagingConstants.BENEFICIARY_ACTIONS_QUEUE_NAME;
	
	public static void main(String[] args) {
		SpringApplication.run(BeneficiaryServiceApplication.class, args);
	}
	
	@Bean
    Queue queueBeneficiaryTimeline() {
        return new Queue(queueNameBeneficiaryTimeline, true);
    }
	
	@Bean
    Queue queueBeneficiaryActions() {
        return new Queue(queueNameBeneficiaryActions, true);
    }
	
	@Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(fanoutExchangeName, true, false);
    }
	
	@Bean
    Binding bindingBeneficiaryFollowing(FanoutExchange exchange) {
    	return BindingBuilder.bind(queueBeneficiaryTimeline()).to(exchange);
    }
	
	@Bean
    Binding bindingBeneficiaryActions(FanoutExchange exchange) {
    	return BindingBuilder.bind(queueBeneficiaryActions()).to(exchange);
    }
}
