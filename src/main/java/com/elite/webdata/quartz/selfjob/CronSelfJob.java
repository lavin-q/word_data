package com.elite.webdata.quartz.selfjob;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * @Description : Corn表达式自定义任务  //描述
 * @Author : qhm  //作者
 * @Date: 2021-01-09 15:25  //时间
 */
@Component
public class CronSelfJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.err.println("Cron表达式自定义任务运行！");
    }
}
