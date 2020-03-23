package app.wegual.poc.scheduler;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
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
import app.wegual.poc.scheduler.jobs.LoginReminderJob;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class QuartzSchedulerApplication {

	static final String directExchangeName = MessagingConstants.directExchange;
	static final String queueNameUserServiceScheduler = MessagingConstants.queueNameUserServiceScheduler;
	static final String userServiceSchedulerRoutingKey = MessagingConstants.userServiceSchedulerRoutingKey;
	
	public static void main(String[] args){
		SpringApplication.run(QuartzSchedulerApplication.class, args);
	}
	
	@Bean
    Queue queueUserServiceScheduler() {
        return new Queue(queueNameUserServiceScheduler, true);
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(directExchangeName, true, false);
    }
    
    @Bean
    Binding bindingLoginReminderTimeline(DirectExchange exchange) {
    	return BindingBuilder.bind(queueUserServiceScheduler()).to(exchange).with(userServiceSchedulerRoutingKey);       
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
