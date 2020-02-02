package app.wegual.poc.es;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.wegual.poc.common.util.MessagingConstants;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class EsRestApplication {
	
    static final String fanoutExchangeName = MessagingConstants.fanoutExchange;
    static final String directExchangeName = MessagingConstants.directExchange;
    
    static final String queueNameBeneficiaryTimeline = MessagingConstants.queueNameBeneficiaryTimeline;
    static final String queueNameUserTimeline = MessagingConstants.queueNameUserTimeline;
    static final String queueNameGiveUpTimeline = MessagingConstants.queueNameGiveUpTimeline;
    
    static final String beneficiaryRoutingKey = MessagingConstants.beneficiaryRoutingKey;
    static final String userRoutingKey = MessagingConstants.userRoutingKey;
    static final String giveUpRoutingKey = MessagingConstants.giveUpRoutingKey;
    
	public static void main(String[] args) {
		SpringApplication.run(EsRestApplication.class, args);
	}
	
//	@Autowired
//    public void setEnv(Environment e)
//    {
//        System.out.println(e.getProperty("msg"));
//    }
	
    @Bean
    Queue queueUserTimeline() {
        return new Queue(queueNameUserTimeline, true);
    }

    @Bean
    Queue queueBeneficiaryTimeline() {
        return new Queue(queueNameBeneficiaryTimeline, true);
    }

    @Bean
    Queue queueGiveUpTimeline() {
        return new Queue(queueNameGiveUpTimeline, true);
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(directExchangeName, true, false);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(fanoutExchangeName, true, false);
    }

    @Bean
    Binding bindingUserTimeline(DirectExchange exchange) {
    	return BindingBuilder.bind(queueUserTimeline()).to(exchange).with(userRoutingKey);
    }

    @Bean
    Binding bindingBeneficiaryTimeline(DirectExchange exchange) {
    	return BindingBuilder.bind(queueBeneficiaryTimeline()).to(exchange).with(beneficiaryRoutingKey);
        
    }
    
    @Bean
    Binding bindingGiveUpTimeline(DirectExchange exchange) {
    	return BindingBuilder.bind(queueGiveUpTimeline()).to(exchange).with(giveUpRoutingKey);       
    }
    
    @Bean
	Binding beneficiaryFanoutBinding(FanoutExchange exchange) {
    	return BindingBuilder.bind(queueBeneficiaryTimeline()).to(exchange);
    }
    
    @Bean
    Binding userFanoutBinding(FanoutExchange exchange) {
    	return BindingBuilder.bind(queueUserTimeline()).to(exchange);
    }
    
    @Bean
    Binding giveUpFanoutBinding(FanoutExchange exchange) {
    	return BindingBuilder.bind(queueGiveUpTimeline()).to(exchange);
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

/*

<! Testing purpose !>

@RefreshScope
@RestController
class MessageRestController {

  @Value("${msg:Hello default}")
  private String message;

  @RequestMapping("/message")
  String getMessage() {
    return this.message;
  }
}

@RefreshScope
@RestController
class RabbitMessageRestController {

  @Value("${spring.rabbitmq.host:0000}")
  private String rabbitHost;

  @RequestMapping("/rabbitHost")
  String getMessage() {
    return this.rabbitHost;
  }
}

*/