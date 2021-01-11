package com.elite.webdata.quartz.selfjob;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * @Description : 打印单词Job  //描述
 * @Author : qhm  //作者
 * @Date: 2021-01-09 14:49  //时间
 */
@Component("CustomizeJob")
public class CustomizeJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.err.println("自定义Job运行！");
    }
}
