package com.elite.webdata.elasticjob;

import lombok.extern.log4j.Log4j2;
import org.apache.shardingsphere.elasticjob.api.listener.ElasticJobListener;
import org.apache.shardingsphere.elasticjob.api.listener.ShardingContexts;
import org.springframework.stereotype.Component;

/**
 * @Description : TestJob 监听  //描述
 * @Author : qhm  //作者
 * @Date: 2021-01-12 10:21  //时间
 */
@Log4j2
@Component("selfJobListener")
public class SelfJobListener implements ElasticJobListener {
    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        log.info("----------------------{}：运行开始前调用----------------------", shardingContexts.getJobName());
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        log.info("----------------------{}：运行结束前调用----------------------", shardingContexts.getJobName());
    }
}
