package app.wegual.poc.restmsgsender;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestMsgSenderApplication {
   static final String topicExchangeName = "spring-boot-exchange";
   
   static final String directExchange = "timeline";
   
   static final String queueNameES = "spring-es";
   static final String queueNameCassandra = "spring-cas";
   
   static final String queueNameBeneficiaryTimeline = "beneficiaryTimeline";
   static final String queueNameUserTimeline = "userTimeline";
   
   @Bean
   Queue queueES() {
       return new Queue(queueNameES, false);
   }

   @Bean
   Queue queueCassandra() {
       return new Queue(queueNameCassandra, false);
   }
   
   @Bean
   Queue queueBeneficiaryTimeline() {
       return new Queue(queueNameBeneficiaryTimeline, true);
   }
   
   @Bean
   Queue queueUserTimeline() {
       return new Queue(queueNameUserTimeline, true);
   }
   
   @Bean
   TopicExchange exchange() {
       return new TopicExchange(topicExchangeName);
   }
   
   @Bean
   DirectExchange exchange1() {
       return new DirectExchange(directExchange);
   }
   
   @Bean
   Binding bindingES(TopicExchange exchange) {
    return BindingBuilder.bind(queueES()).to(exchange).with("es-cas");
   }
   
   @Bean
   Binding binding(TopicExchange exchange) {
    return BindingBuilder.bind(queueCassandra()).to(exchange).with("es-cas");
       
   }
   
   @Bean
   Binding bindingBEN(DirectExchange exchange) {
    return BindingBuilder.bind(queueBeneficiaryTimeline()).to(exchange).with("beneficiary");
   }

   @Bean
   Binding bindingUS(DirectExchange exchange) {
    return BindingBuilder.bind(queueUserTimeline()).to(exchange).with("user");
   }
   
   public static void main(String[] args) {
	   SpringApplication.run(RestMsgSenderApplication.class, args);
	}

}