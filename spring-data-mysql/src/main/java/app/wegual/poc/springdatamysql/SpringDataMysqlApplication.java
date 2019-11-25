package app.wegual.poc.springdatamysql;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import app.wegual.poc.common.util.MessagingConstants;
import app.wegual.poc.springdatamysql.events.BeneficiaryEventHandler;
import app.wegual.poc.springdatamysql.events.GiveUpFollowerEventHandler;
import app.wegual.poc.springdatamysql.events.PledgeEventHandler;
import app.wegual.poc.springdatamysql.events.UserEventHandler;
import app.wegual.poc.springdatamysql.events.UserFollowersEventHandler;

@EntityScan(basePackages = "app.wegual.poc.common.model")
@SpringBootApplication
public class SpringDataMysqlApplication implements CommandLineRunner {
	
    static final String exchangeName = MessagingConstants.directExchange;
    static final String queueNameUserTimeline = MessagingConstants.queueNameUserTimeline;
    static final String queueNameBeneficiaryTimeline = MessagingConstants.queueNameBeneficiaryTimeline;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringDataMysqlApplication.class, args);
	}
	
    @Bean
    Queue queueUserTimeline() {
        return new Queue(queueNameUserTimeline, true);
    }

    @Bean
    Queue queueBeneficiaryTimeline() {
        return new Queue(queueNameBeneficiaryTimeline, true);
    }
    
    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchangeName, true, false);
    }

    @Bean
    Binding bindingUserTimeline(DirectExchange exchange) {
    	return BindingBuilder.bind(queueUserTimeline()).to(exchange).with(MessagingConstants.userRoutingKey);
    }

    @Bean
    Binding bindingBeneficiaryTimeline(DirectExchange exchange) {
    	return BindingBuilder.bind(queueBeneficiaryTimeline()).to(exchange).with(MessagingConstants.beneficiaryRoutingKey);
        
    }

	@Bean
	PledgeEventHandler pledgeEventHandler() {
		return new PledgeEventHandler();
	}

	@Bean
	BeneficiaryEventHandler beneficiaryEventHandler() {
		return new BeneficiaryEventHandler();
	}
	
	@Bean
	UserEventHandler userEventHandler() {
		return new UserEventHandler();
	}
	
	@Bean
	UserFollowersEventHandler userFollowersEventHandler() {
		return new UserFollowersEventHandler();
	}
	
	@Bean
	GiveUpFollowerEventHandler giveUpFollowersEventHandler() {
		return new GiveUpFollowerEventHandler();
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
	
	public void run(String... args) {
//		try {
//			userRepository.save(new User("Michael Jackson", "jacksonm@gmail.com"));
//		} catch (DataIntegrityViolationException dive) {
//			System.out.println("User already exists with email");
//		}
//		User owner = userRepository.findByEmail("jacksonm@gmail.com").get(0);
//		
//		// create new beneficiaries and persist
//		Beneficiary ben = null;
//		try {
//			ben = beneficiaryRepository.findByName(BENEFICIARY_NAME);
//			if(ben == null)
//			{
//				ben = new Beneficiary();
//				ben.setName(BENEFICIARY_NAME);
//				ben.setUrl("https://www.xyz.org");
//				ben.setOwner(owner);
//				ben.setBeneficiaryType(BeneficiaryType.NONPROFIT);
//				beneficiaryRepository.save(ben);
//			}
//		} catch (Exception dive) {
//			System.out.println("Exception reading/creating Beneficiary");
//			//return;
//		}
//		
//		Pledge pledge = new Pledge();
//		pledge.setBeneficiary(ben);
//		pledge.setPledgedBy(owner);
//		pledge.setAmount(200.00);
//		pledge.setCurrency(Currency.getInstance("USD"));
//		pledge.setPledgedDate(new Date());
//		
//		pledgeRepository.save(pledge);
	}
}
