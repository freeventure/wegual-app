package app.wegual.poc.scheduler.triggers;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import app.wegual.poc.scheduler.jobs.LoginReminderJob;

public class loginReminderTrigger {
	
	public static void schedule() throws SchedulerException{
	String exp = "0 0 0 1/1 * ? *";

    SchedulerFactory factory = new StdSchedulerFactory();
    Scheduler scheduler = factory.getScheduler();
    scheduler.start();
    JobDetail job = JobBuilder.newJob(LoginReminderJob.class).build();
    Trigger trigger = TriggerBuilder.newTrigger()
                                    .startNow()
                                    .withSchedule(
                                         CronScheduleBuilder.cronSchedule(exp))
                                    .build();
    scheduler.scheduleJob(job, trigger);
	}
}
