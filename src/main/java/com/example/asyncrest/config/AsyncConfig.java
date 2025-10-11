package com.example.asyncrest.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);       // min threads always alive
        executor.setMaxPoolSize(10);       // max threads allowed
        executor.setQueueCapacity(50);     // queued tasks before rejecting
        executor.setThreadNamePrefix("AsyncExecutor-");
        executor.initialize();
        return executor;
    }


}

