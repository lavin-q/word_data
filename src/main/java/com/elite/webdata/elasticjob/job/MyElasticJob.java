package com.elite.webdata.elasticjob.job;

import lombok.extern.log4j.Log4j2;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.dataflow.job.DataflowJob;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description :   //描述
 * @Author : qhm  //作者
 * @Date: 2021-01-13 16:37  //时间
 */
@Log4j2
@Component("myElasticJob")
public class MyElasticJob implements DataflowJob<String> {
    //抓取数据
    @Override
    public List<String> fetchData(ShardingContext shardingContext) {
        log.info("----------------------{}抓取数据开始----------------------", shardingContext.getJobName());
        List<String> strings = new ArrayList<String>(){{
            add("1");
            add("2");
            add("3");
            add("4");
            add("5");
            add("6");
            add("7");
        }};

        return strings;
       //return null;
    }

    //当抓取数据不为空时该方法执行
    @Override
    public void processData(ShardingContext shardingContext, List<String> data) {
        log.info("----------------------{}processData执行----------------------", shardingContext.getJobName());
        if (data != null && data.size() > 0) {
           int a = data.size() / shardingContext.getShardingTotalCount();

        }

    }
}
