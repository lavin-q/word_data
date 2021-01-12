package com.elite.webdata.elasticjob.init;

import com.elite.webdata.elasticjob.entity.TbJob;
import com.elite.webdata.elasticjob.mapper.TbJobMapper;
import com.elite.webdata.elasticjob.service.ElasticJobService;
import com.elite.webdata.rabbitMQ.utils.SpringUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.shardingsphere.elasticjob.api.ElasticJob;
import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.lite.api.bootstrap.impl.ScheduleJobBootstrap;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperConfiguration;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @Description :   //描述
 * @Author : qhm  //作者
 * @Date: 2021-01-11 15:59  //时间
 */
@Log4j2
@Component("elasticJobUtil")
public class ElasticJobUtil {

    @Resource(name = "elasticJobService")
    private ElasticJobService elasticJobService;

    @Resource
    private TbJobMapper tbJobMapper;

    @Value("${zookeeper.address}")
    private String zkAddress;

    @Resource
    private SpringUtil springUtil;

    public CoordinatorRegistryCenter createRegistryCenter(String zkAddress, String nameSpace) {
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration(zkAddress, nameSpace));
        regCenter.init();
        return regCenter;
    }

    public JobConfiguration createJobConfiguration(String jobName, int shardingCount, String CronExpression) {
        return JobConfiguration.newBuilder(jobName, shardingCount).cron(CronExpression).build();

    }

    @PostConstruct
    public void initScheduler() {
        try {
            List<TbJob> tbJobs = this.tbJobMapper.selectByExample(null);
            for (TbJob job : tbJobs) {
                SimpleJob myJob = (SimpleJob)springUtil.getBean(job.getClassName());
                System.out.println(Objects.isNull(myJob));
                new ScheduleJobBootstrap(createRegistryCenter(zkAddress,job.getNameSpace()), myJob, createJobConfiguration(job.getJobName(),job.getShardingNum(),job.getCron())).schedule();
            }
        } catch (Exception e) {
            log.error("初始化作业出错{}",e.getMessage());
        }
    }

}
