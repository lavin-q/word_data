package com.elite.webdata.elasticjob;

import org.apache.shardingsphere.elasticjob.api.ElasticJob;
import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.lite.api.bootstrap.impl.ScheduleJobBootstrap;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperConfiguration;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;

/**
 * @Description : 定时任务Demo  //描述
 * @Author : qhm  //作者
 * @Date: 2021-01-08 10:57  //时间
 */
public class MyJobDemo {

    public static void main(String[] args) throws Exception {
        ElasticJob myJob = (ElasticJob)Class.forName("com.elite.webdata.elasticjob.MyJob").newInstance();
        new ScheduleJobBootstrap(createRegistryCenter(), myJob, createJobConfiguration()).schedule();
    }

    private static CoordinatorRegistryCenter createRegistryCenter() {
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration("127.0.0.1:2181", "my-job"));
        regCenter.init();
        return regCenter;
    }

    private static JobConfiguration createJobConfiguration() {
        JobConfiguration jobConfig = JobConfiguration.newBuilder("MyJob", 3).cron("0/5 * * * * ?").build();
        return jobConfig;
    }
}
