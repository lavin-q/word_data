package com.elite.webdata.quartz;

import com.elite.webdata.quartz.selfjob.CronSelfJob;
import com.elite.webdata.quartz.selfjob.CustomizeJob;
import com.elite.webdata.quartz.selfjob.PrintWordsJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @Description :   //描述
 * @Author : qhm  //作者
 * @Date: 2021-01-09 13:45  //时间
 */
public class MyScheduler {

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        // 1、创建调度器Scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        //
        Scheduler scheduler = schedulerFactory.getScheduler();
        System.out.println("--------scheduler start ! ------------");
       /* // 2、创建JobDetail实例，并与PrintWordsJob类绑定(Job执行内容)
        JobDetail jobDetail = JobBuilder.newJob(PrintWordsJob.class)
                .withIdentity("job1", "group1").build();

        // 3、构建Trigger实例,每隔1s执行一次
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "triggerGroup1")
                .startNow()//立即生效
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)//每隔1s执行一次
                        .repeatForever()).build();//一直执行

        //4、执行
        scheduler.scheduleJob(jobDetail, trigger);*/

        //scheduler.start();


        //添加Job
        /*JobDetail jobDetail1 = JobBuilder.newJob(CustomizeJob.class)
                .withIdentity("job2", "group2").build();
        Trigger trigger1 = TriggerBuilder.newTrigger()
                .withIdentity("trigger2", "triggerGroup2")
                .forJob("job2", "group2")
                .startNow()//立即生效
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(3)//每隔3s执行一次
                        .repeatForever()).build();//一直执行
        scheduler.addJob(jobDetail1,false,true);
        //JobKey jobKey = new JobKey("job2","group2");
        //JobDataMap jobDataMap = new JobDataMap();
        scheduler.scheduleJob(trigger1);
        //scheduler.triggerJob(jobKey);*/

        //添加CronJob
        JobDetail cronJobDetail = JobBuilder.newJob(CronSelfJob.class)
                .usingJobData("cronJobDetail","自定义Cron定时任务")
                .withIdentity("corn-self-job", "group-corn").build();

        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("cron-trigger", "cron-trigger-group")
                .forJob("corn-self-job","group-corn")
                .usingJobData("cronJobDetail", "这是jobDetail1的trigger")
                .startNow()//立即生效
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ? "))
                .build();
        scheduler.addJob(cronJobDetail,false,true);
        scheduler.scheduleJob(cronTrigger);

        //启动脚本
        scheduler.start();


        //睡眠,主线程休眠一分钟，定时任务运行一分钟，不休眠时代码向下直接运行，使程序运行结束
        TimeUnit.MINUTES.sleep(1);
        scheduler.shutdown();
        System.out.println("--------scheduler shutdown ! ------------");

    }
}
