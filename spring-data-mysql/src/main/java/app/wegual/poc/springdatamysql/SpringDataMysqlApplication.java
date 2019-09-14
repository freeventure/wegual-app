package app.wegual.poc.springdatamysql;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import app.wegual.poc.springdatamysql.events.BeneficiaryEventHandler;
import app.wegual.poc.springdatamysql.events.PledgeEventHandler;

@EntityScan(basePackages = "app.wegual.poc.common.model")
@SpringBootApplication
public class SpringDataMysqlApplication implements CommandLineRunner {
	
//	private static final String BENEFICIARY_NAME = "XYZ Foundation";
    static final String topicExchangeName = "spring-boot-exchange";

    static final String queueNameES = "spring-es-pledges";
    static final String queueNameCassandra = "spring-cas-pledges";

    static final String queueNameESBen = "spring-es-beneficiaries";
    static final String queueNameCassandraBen = "spring-cas-beneficiaries";
    
//	@Autowired
//	private UserPagingAndSortingRepository userRepository;
//
//	@Autowired
//	private BeneficiaryRepository beneficiaryRepository;
//	
//	@Autowired
//	private PledgeRepository pledgeRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringDataMysqlApplication.class, args);
	}
	
    @Bean
    Queue queueES() {
        return new Queue(queueNameES, false);
    }

    @Bean
    Queue queueCassandra() {
        return new Queue(queueNameCassandra, false);
    }

    @Bean
    Queue queueESBen() {
        return new Queue(queueNameESBen, false);
    }

    @Bean
    Queue queueCassandraBen() {
        return new Queue(queueNameCassandraBen, false);
    }
    
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding bindingESPledges(TopicExchange exchange) {
    	return BindingBuilder.bind(queueES()).to(exchange).with("pledges");
    }

    @Bean
    Binding bindingCASPledges(TopicExchange exchange) {
    	return BindingBuilder.bind(queueCassandra()).to(exchange).with("pledges");
        
    }

    @Bean
    Binding bindingESBeneficiary(TopicExchange exchange) {
    	return BindingBuilder.bind(queueESBen()).to(exchange).with("beneficiaries");
    }

    @Bean
    Binding bindingCASBeneficiary(TopicExchange exchange) {
    	return BindingBuilder.bind(queueCassandraBen()).to(exchange).with("beneficiaries");
        
    }

	@Bean
	PledgeEventHandler pledgeEventHandler() {
		return new PledgeEventHandler();
	}

	@Bean
	BeneficiaryEventHandler beneficiaryEventHandler() {
		return new BeneficiaryEventHandler();
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
