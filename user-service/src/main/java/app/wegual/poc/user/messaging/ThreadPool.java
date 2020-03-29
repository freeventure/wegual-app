package app.wegual.poc.user.messaging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPool {
	
	@Bean(name = "timelineSenderPool")
	public TaskExecutor timelineMessageSender() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);
		executor.setThreadNamePrefix("message_sender_thread");
		executor.initialize();
		return executor;
	}
	
	@Bean(name = "loginReminderSenderPool")
	public TaskExecutor loginReminderMessageSender() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);
		executor.setThreadNamePrefix("login_reminder_message_sender_thread");
		executor.initialize();
		return executor;
	}
}
