package app.wegual.poc.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;

import app.wegual.poc.common.util.MessagingConstants;

@EntityScan(basePackages = "app.wegual.poc.common.model")
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class UserServiceApplication {

	static final String directExchangeName = MessagingConstants.directExchange;
	static final String queueNameUserTimeline = MessagingConstants.queueNameUserTimeline;
	static final String userRoutingKey = MessagingConstants.userRoutingKey;
	static final String loginReminderRoutingKey = MessagingConstants.loginReminderRoutingKey;
	static final String queueNameLoginReminderMailService = MessagingConstants.queueNameLoginReminderMailService;

	
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
	
	@Bean
    Queue queueUserTimeline() {
        return new Queue(queueNameUserTimeline, true);
    }
	
	@Bean
    Queue queueLoginReminderMailService() {
        return new Queue(queueNameLoginReminderMailService, true);
    }
	
	@Bean
    DirectExchange directExchange() {
        return new DirectExchange(directExchangeName, true, false);
    }
	
	@Bean
    Binding bindingUserTimeline(DirectExchange exchange) {
    	return BindingBuilder.bind(queueUserTimeline()).to(exchange).with(userRoutingKey);
    }
	
	@Bean
    Binding bindingLoginReminderMailService(DirectExchange exchange) {
    	return BindingBuilder.bind(queueLoginReminderMailService()).to(exchange).with(loginReminderRoutingKey);
    }
    
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
	    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
	    rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
	    return rabbitTemplate;
	}
	
	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

}

