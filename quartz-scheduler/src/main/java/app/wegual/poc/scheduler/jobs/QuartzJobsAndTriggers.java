package app.wegual.poc.scheduler.jobs;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
public class QuartzJobsAndTriggers {
	
	@Bean("loginReminder")
	public JobDetailFactoryBean jobDetail() {
	    JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
	    jobDetailFactory.setJobClass(LoginReminderJob.class);
	    jobDetailFactory.setName("LoginReminderJob");
	    jobDetailFactory.setDurability(true);
	    return jobDetailFactory;
	}
	
	@Bean("loginReminderTrigger")
	public SimpleTriggerFactoryBean trigger(@Qualifier("loginReminder") JobDetail job) {
	    SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
	    trigger.setJobDetail(job);
	    trigger.setName("Login Reminder Trigger");
	    trigger.setStartDelay(6000);
	    trigger.setRepeatInterval(60000);
	    trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
	    return trigger;
	}
}
