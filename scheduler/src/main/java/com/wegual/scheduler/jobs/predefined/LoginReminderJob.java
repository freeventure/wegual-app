package com.wegual.scheduler.jobs.predefined;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
public class LoginReminderJob implements Job {

	@Override
    public void execute(JobExecutionContext context) {
		System.out.println("Hey, I ran from Quartz");
	}
}
