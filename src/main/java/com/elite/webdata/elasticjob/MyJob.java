package com.elite.webdata.elasticjob;

import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.stereotype.Component;

/**
 * @Description : 定时任务  //描述
 * @Author : qhm  //作者
 * @Date: 2021-01-08 10:45  //时间
 */
@Component("MyJob")
public class MyJob implements SimpleJob {

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
