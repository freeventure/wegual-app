package app.wegual.poc.scheduler.triggers;

import org.springframework.context.ApplicationContext;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.CalendarIntervalScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;

import javax.sql.DataSource;

import static org.quartz.DateBuilder.*;

import app.wegual.poc.scheduler.jobs.LoginReminderJob;

@Configuration
public class JobScheduler {
	@Autowired
	ApplicationContext applicationContext;
	@Bean
	public SchedulerFactoryBean scheduler(Trigger trigger, JobDetail job) {
	    SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
	    schedulerFactory.setJobFactory(springBeanJobFactory());
	    schedulerFactory.setJobDetails(job);
	    schedulerFactory.setTriggers(trigger);
	    return schedulerFactory;
	}

	@Bean
	public SpringBeanJobFactory springBeanJobFactory() {
	    AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
	    jobFactory.setApplicationContext(applicationContext);
	    return jobFactory;
	}
}
