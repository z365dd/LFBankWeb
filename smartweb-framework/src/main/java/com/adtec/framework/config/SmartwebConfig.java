package com.adtec.framework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.adtec.scheduler.config.SchedulerConfig;
import com.adtec.sys.pool.config.SmThreadPoolConfig;

@Configuration
@EnableAspectJAutoProxy
@Import({SmThreadPoolConfig.class, SchedulerConfig.class})
public class SmartwebConfig {


}
