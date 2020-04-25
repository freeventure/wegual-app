package com.wegual.beneficiaryservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncExecutionConfig {
	
	@Bean(name="executorMessageSender")
    public TaskExecutor executorMessageSender() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setThreadNamePrefix("message_sender_thread");
        executor.initialize();
        return executor;
    } 

	@Bean(name="executorESActions")
    public TaskExecutor executorESActions() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setThreadNamePrefix("es_asynch_thread");
        executor.initialize();
        return executor;
    } 

}
