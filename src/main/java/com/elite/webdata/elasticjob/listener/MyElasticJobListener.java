package com.elite.webdata.elasticjob.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.listener.ElasticJobListener;
import org.apache.shardingsphere.elasticjob.api.listener.ShardingContexts;
import org.springframework.stereotype.Component;

/**
 * @Description :   //描述
 * @Author : qhm  //作者
 * @Date: 2021-01-14 11:01  //时间
 */
@Slf4j
@Component("myElasticJobListener")
public class MyElasticJobListener implements ElasticJobListener {
    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        log.info("----------------------{}：运行开始前调用----------------------", shardingContexts.getJobName());
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        log.info("----------------------{}：运行结束前调用----------------------", shardingContexts.getJobName());
    }
}
