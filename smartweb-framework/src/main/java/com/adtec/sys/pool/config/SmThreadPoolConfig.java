package com.adtec.sys.pool.config;

import com.adtec.sys.pool.SmThreadPoolExecutor;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class SmThreadPoolConfig {

    @Bean
    public SmThreadPoolExecutor initThreadPoolExecutor() {
        SmThreadPoolProperties threadPoolProperties = new SmThreadPoolProperties();
        return new SmThreadPoolExecutor(
                // 线程池核心池的大小。
                threadPoolProperties.getCorePoolSize(),
                // 线程池的最大线程数。
                threadPoolProperties.getMaximumPoolSize(),
                // 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间。
                threadPoolProperties.getKeepAliveTime(),
                // keepAliveTime 的时间单位。
                TimeUnit.MILLISECONDS,
                // 用来储存等待执行任务的队列。
                new LinkedBlockingQueue<Runnable>(1024),
                // 线程工厂。
                new ThreadFactoryBuilder().setNameFormat("smartweb-thread-pool-%d").build(),
                // 拒绝策略。
                new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
