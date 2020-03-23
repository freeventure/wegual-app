package com.wegual.scheduler.jobs.predefined;

import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import com.wegual.scheduler.QuartzUtils;

import feign.Logger;

@Configuration
public class AutoSubmitJobs {
	
	@Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
	
	@Bean(name = "loginReminder")
    public JobDetailFactoryBean jobMemberStats() {
        return QuartzUtils.createJobDetail(LoginReminderJob.class, "Login Reminder Job");
    }
    @Bean(name = "loginReminderTrigger")
    public SimpleTriggerFactoryBean triggerMemberStats(@Qualifier("loginReminder") JobDetail jobDetail) {
        return QuartzUtils.createTrigger(jobDetail, 10000, 0, "Login Reminder Job Trigger");
    }
}
