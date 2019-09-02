package app.wegual.poc.restmsgsender;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestMsgSenderApplication {
    static final String topicExchangeName = "spring-boot-exchange";

    static final String queueNameES = "spring-es";
    static final String queueNameCassandra = "spring-cas";

    @Bean
    Queue queueES() {
        return new Queue(queueNameES, false);
    }

    @Bean
    Queue queueCassandra() {
        return new Queue(queueNameCassandra, false);
    }
    
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding bindingES(TopicExchange exchange) {
    	return BindingBuilder.bind(queueES()).to(exchange).with("es-cas");
    }

    @Bean
    Binding binding(TopicExchange exchange) {
    	return BindingBuilder.bind(queueCassandra()).to(exchange).with("es-cas");
        
    }

	public static void main(String[] args) {
		SpringApplication.run(RestMsgSenderApplication.class, args);
	}

}
