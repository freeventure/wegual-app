package app.wegual.poc.es.messaging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
//@EnableAsync
public class ThreadPool {
	@Bean(name = "senderPool")
	public TaskExecutor messageSender() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(4);
		executor.setMaxPoolSize(8);
		executor.setThreadNamePrefix("message_sender_thread");
		executor.initialize();
		return executor;
	}
}
