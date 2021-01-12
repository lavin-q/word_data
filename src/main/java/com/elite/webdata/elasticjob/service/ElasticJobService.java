package com.elite.webdata.elasticjob.service;

import com.elite.webdata.elasticjob.entity.TbJob;
import com.elite.webdata.elasticjob.mapper.TbJobMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description : Elastic job service  //描述
 * @Author : qhm  //作者
 * @Date: 2021-01-11 17:43  //时间
 */
@Service("elasticJobService")
public class ElasticJobService {

    private List<TbJob> jobList;

    @Resource
    private TbJobMapper tbJobMapper;

    public List<TbJob> getAllJobs() {
        jobList = this.tbJobMapper.selectByExample(null);
        return jobList;
    }

    public List<TbJob> getJobList() {
        return jobList;
    }

    public void setJobList(List<TbJob> jobList){
        this.jobList = jobList;
    }
}
