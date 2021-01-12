package com.elite.webdata.quartz.service;

import com.elite.webdata.quartz.dto.JobConfigurationDto;
import com.elite.webdata.quartz.dto.JobDetailDto;
import com.elite.webdata.quartz.dto.JobTriggerDto;
import com.elite.webdata.rabbitMQ.utils.SpringUtil;
import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Description : QuartzService  //描述
 * @Author : qhm  //作者
 * @Date: 2021-01-10 13:50  //时间
 */
@Service("quartzService")
@Log4j2
public class QuartzService {

    @Resource(name = "initializeScheduler")
    private Scheduler initializeScheduler;
    /*@Resource(name = "springUtil")
    private SpringUtil springUtil;*/

    /**
     * 添加时间任务
     *
     * @param jobConfigurationDto 定时任务配置实体类
     * @return 操作结果
     */
    public Object addQuartzCronJob(JobConfigurationDto jobConfigurationDto) {
        try {
            if (Objects.isNull(initializeScheduler)) {
                log.info("获取Scheduler实例为空");
            } else {
                log.info("Scheduler 启动状态:{}", initializeScheduler.isStarted() ? "运行中" : "未启动");
                log.info("成功获取Scheduler实例");
            }

            JobDetailDto jobDetail = jobConfigurationDto.getJobDetail();
            JobTriggerDto jobTrigger = jobConfigurationDto.getJobTrigger();
            String jobClassName = jobDetail.getJobClassName();
            Job bean = (Job) SpringUtil.getBean(jobClassName);
            JobDetail cronJobDetail = JobBuilder.newJob(bean.getClass())
                    .usingJobData(jobDetail.getJobDataKey(), jobDetail.getJobDataValue())
                    .withIdentity(jobDetail.getJobName(), jobDetail.getJobGroupName()).build();
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobTrigger.getJobDataKey(), jobTrigger.getTriggerGroupName())
                    .forJob(jobDetail.getJobName(), jobDetail.getJobGroupName())
                    //.usingJobData("cronJobDetail", "这是cronJobDetail的trigger")
                    .startNow()//立即生效
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobTrigger.getCronExpression()))
                    .build();
            initializeScheduler.addJob(cronJobDetail, false, true);
            initializeScheduler.scheduleJob(cronTrigger);
        } catch (SchedulerException e) {
            log.error("捕获异常:{}", e.getMessage());
            return "出现异常!";
        }
        return "添加QuartzJob成功!";
    }

    public Object removeQuartzCronJob(String jobName, String jobGroup) {
        try {
            JobKey jobKey = new JobKey(jobName, jobGroup);
            //判断是否正在运行
            TriggerKey triggerKey = new TriggerKey("test-trigger1", "test-trigger-group1");
            Trigger.TriggerState triggerState = initializeScheduler.getTriggerState(triggerKey);
            log.info("当前Job运行状态:{}", triggerState);
            boolean b = initializeScheduler.checkExists(jobKey);
            if (b) {
                log.info("移除QuartzJob,JobName:{},JobGroup:{}成功", jobName, jobGroup);
                initializeScheduler.deleteJob(jobKey);
            } else {
                log.info("未查询到移除QuartzJob，JobName:{},JobGroup:{}成功", jobName, jobGroup);
                return "未查询到指定QuartzJob!";
            }
        } catch (SchedulerException e) {
            log.error("捕获异常:{}", e.getMessage());
            return "出现异常!";
        }
        return "移除QuartzJob成功!";
    }
}
