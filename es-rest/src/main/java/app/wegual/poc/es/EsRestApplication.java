package app.wegual.poc.es;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;

import app.wegual.poc.common.util.MessagingConstants;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class EsRestApplication {
	
    static final String fanoutExchangeName = MessagingConstants.fanoutExchange;
    static final String directExchangeName = MessagingConstants.directExchange;
    static final String queueNameTimeline = MessagingConstants.queueNameTimeline;
	
	public static void main(String[] args) {
		SpringApplication.run(EsRestApplication.class, args);	
	}
	
    @Bean
    Queue queueTimeline() {
        return new Queue(queueNameTimeline, true);
    }

//    @Bean
//    Queue queueBeneficiaryTimeline() {
//        return new Queue(queueNameBeneficiaryTimeline, true);
//    }
//
//    @Bean
//    Queue queueGiveUpTimeline() {
//        return new Queue(queueNameGiveUpTimeLine, true);
//    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(directExchangeName, true, false);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(fanoutExchangeName, true, false);
    }

    @Bean
    Binding bindingTimeline(DirectExchange exchange) {
    	return BindingBuilder.bind(queueTimeline()).to(exchange).with(MessagingConstants.timelineRoutingKey);
    }

//    @Bean
//    Binding bindingBeneficiaryTimeline(DirectExchange exchange) {
//    	return BindingBuilder.bind(queueBeneficiaryTimeline()).to(exchange).with(MessagingConstants.beneficiaryRoutingKey);
//        
//    }
//    
//    @Bean
//    Binding bindingGiveUpTimeline(DirectExchange exchange) {
//    	return BindingBuilder.bind(queueGiveUpTimeline()).to(exchange).with(MessagingConstants.giveUpRoutingKey);
//        
//    }
    
//    @Bean
//    Binding beneficiaryFanoutBinding(FanoutExchange exchange) {
//    	return BindingBuilder.bind(queueBeneficiaryTimeline()).to(exchange);
//    }
//    
//    @Bean
//    Binding userFanoutBinding(FanoutExchange exchange) {
//    	return BindingBuilder.bind(queueUserTimeline()).to(exchange);
//    }
//    
//    @Bean
//    Binding giveUpFanoutBinding(FanoutExchange exchange) {
//    	return BindingBuilder.bind(queueGiveUpTimeline()).to(exchange);
//    }

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
