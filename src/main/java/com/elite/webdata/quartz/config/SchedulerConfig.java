package com.elite.webdata.quartz.config;

import com.elite.webdata.quartz.selfjob.CronSelfJob;
import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description :   //描述
 * @Author : qhm  //作者
 * @Date: 2021-01-10 12:59  //时间
 */
@Configuration
@Log4j2
public class SchedulerConfig {

    @Bean(name = "initializeScheduler")
    public Scheduler initializeScheduler() throws SchedulerException {
        //初始化 Scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        boolean started = scheduler.isStarted();
        log.info("scheduler实例是否启动:"+started);
        if (!started) {
            scheduler.start();
            started = scheduler.isStarted();
        }
        log.info("scheduler实例是否启动:"+started);
        return scheduler;


    }
}
