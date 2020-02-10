package com.wegual.mailservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

// Class for configuring thread pool for asynchronous mail sending
@Configuration
public class AsyncExecutionConfig {

	@Bean(name="executorMailSender")
    public TaskExecutor executorMailSender() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setThreadNamePrefix("mail_sender_thread");
        executor.initialize();
        return executor;
    } 
}
