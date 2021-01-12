package com.elite.webdata.elasticjob.job;

import lombok.extern.log4j.Log4j2;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.api.listener.ElasticJobListener;
import org.apache.shardingsphere.elasticjob.api.listener.ShardingContexts;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.stereotype.Component;

/**
 * @Description : 测试Job  //描述
 * @Author : qhm  //作者
 * @Date: 2021-01-11 15:55  //时间
 */
@Log4j2
@Component("testJob")
public class TestJob implements SimpleJob, ElasticJobListener {

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        log.info("---------------------自定义定时任务开始----------------------------");
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        log.info("---------------------自定义定时任务结束----------------------------");
    }
    @Override
    public void execute(ShardingContext context) {

        String jobName = context.getJobName();
        log.info("正在运行的JOB:{}", jobName);
    }
}
