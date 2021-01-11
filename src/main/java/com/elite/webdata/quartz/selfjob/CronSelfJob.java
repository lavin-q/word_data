package com.elite.webdata.quartz.selfjob;

import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.stereotype.Component;

/**
 * @Description : Corn表达式自定义任务  //描述
 * @Author : qhm  //作者
 * @Date: 2021-01-09 15:25  //时间
 */
@Component("CronSelfJob")
@Log4j2
public class CronSelfJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey key = context.getJobDetail().getKey();
        log.info("Cron表达式自定义任务运行:{}", key.getName());
        System.err.println("Cron表达式自定义任务运行:" + key.getName());
    }
}
