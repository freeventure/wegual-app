package com.wegual.userservice;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import app.wegual.common.util.MessagingConstants;

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

	private static final String fanoutExchangeName = MessagingConstants.USERACTIONS_FANOUT_EXCHANGE_NAME;
	private static final String queueNameUserFollowing = MessagingConstants.USER_FOLLOWING_QUEUE_NAME;
	private static final String queueNameUserTimeline = MessagingConstants.USER_TIMELINE_QUEUE_NAME;
	private static final String queueNameUserAction = MessagingConstants.USER_ACTION_QUEUE_NAME;

	@Bean
	Queue queueUserAction() {
		return new Queue(queueNameUserAction, true);
	}
	
	@Bean
	Queue queueUserFollowing() {
		return new Queue(queueNameUserFollowing, true);
	}

	@Bean
	Queue queueUserTimeline() {
		return new Queue(queueNameUserTimeline, true);
	}
	
	@Bean
	Queue queueTwitterAction() {
		return new Queue(MessagingConstants.TWITTER_ACTION_QUEUE_NAME, true);
	}
	
	@Bean
	DirectExchange directExchange() {
		return new DirectExchange(MessagingConstants.TWITTER_ACTION_EXCHANGE, true, false);
	}

	@Bean
	FanoutExchange fanoutExchange() {
		return new FanoutExchange(fanoutExchangeName, true, false);
	}

	@Bean
	Binding bindingUserFollowing(FanoutExchange exchange) {
		return BindingBuilder.bind(queueUserFollowing()).to(exchange);
	}

	@Bean
	Binding bindingUserTimeline(FanoutExchange exchange) {
		return BindingBuilder.bind(queueUserTimeline()).to(exchange);
	}
	
	@Bean
	Binding bindingUserAction(FanoutExchange exchange) {
		return BindingBuilder.bind(queueUserAction()).to(exchange);
	}
	
	@Bean
	Binding bindingTwitterAction(DirectExchange directExchange) {
		return BindingBuilder.bind(queueTwitterAction()).to(directExchange).with(MessagingConstants.TWITTER_ACTION_ROUTING_KEY);
	}
	
	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
