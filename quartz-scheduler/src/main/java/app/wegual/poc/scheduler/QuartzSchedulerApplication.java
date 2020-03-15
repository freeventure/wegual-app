package app.wegual.poc.scheduler;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import app.wegual.poc.common.util.MessagingConstants;

@SpringBootApplication
public class QuartzSchedulerApplication {

	static final String directExchangeName = MessagingConstants.directExchange;
	static final String queueNameLoginReminderTimeline = MessagingConstants.queueNameLoginReminderTimeline;
	static final String loginReminderRoutingKey = MessagingConstants.loginReminderRoutingKey;
	
	public static void main(String[] args) {
		SpringApplication.run(QuartzSchedulerApplication.class, args);
	}
	
	@Bean
    Queue queueLoginReminderTimeline() {
        return new Queue(queueNameLoginReminderTimeline, true);
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(directExchangeName, true, false);
    }
    
    @Bean
    Binding bindingLoginReminderTimeline(DirectExchange exchange) {
    	return BindingBuilder.bind(queueLoginReminderTimeline()).to(exchange).with(loginReminderRoutingKey);       
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