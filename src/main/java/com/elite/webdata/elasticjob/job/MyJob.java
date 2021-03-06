package com.elite.webdata.elasticjob.job;

import lombok.extern.log4j.Log4j2;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.api.listener.ElasticJobListener;
import org.apache.shardingsphere.elasticjob.api.listener.ShardingContexts;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.stereotype.Component;

/**
 * @Description : 定时任务  //描述
 * @Author : qhm  //作者
 * @Date: 2021-01-08 10:45  //时间
 */
@Log4j2
@Component("MyJob")
public class MyJob implements SimpleJob, ElasticJobListener {
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
        switch (context.getShardingItem()) {
            case 0:
                System.out.println("----------------------分片0----------------------");
                break;
            case 1:
                System.out.println("----------------------分片1----------------------");
                break;
            case 2:
                System.out.println("----------------------分片2----------------------");
                break;
        }
    }


}
