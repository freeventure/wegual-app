package app.wegual.poc.springdatamysql;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
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
	
    static final String exchangeName = "timeline";

    static final String queueNameUserTimeline = "userTimeline";
    static final String queueNameBeneficiaryTimeline = "beneficiaryTimeline";
	
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
        return new DirectExchange(exchangeName);
    }

    @Bean
    Binding bindingUserTimeline(DirectExchange exchange) {
    	return BindingBuilder.bind(queueUserTimeline()).to(exchange).with("user");
    }

    @Bean
    Binding bindingBeneficiaryTimeline(DirectExchange exchange) {
    	return BindingBuilder.bind(queueBeneficiaryTimeline()).to(exchange).with("beneficiary");
        
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
