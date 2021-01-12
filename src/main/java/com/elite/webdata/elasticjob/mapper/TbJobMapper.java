package com.elite.webdata.elasticjob.mapper;


import com.elite.webdata.elasticjob.entity.TbJob;
import com.elite.webdata.elasticjob.entity.TbJobExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.elasticsearch.annotations.Mapping;

import java.util.List;

@Mapping
public interface TbJobMapper {
    int countByExample(TbJobExample example);

    int deleteByExample(TbJobExample example);

    int deleteByPrimaryKey(Long jobId);

    int insert(TbJob record);

    int insertSelective(TbJob record);

    List<TbJob> selectByExample(TbJobExample example);

    TbJob selectByPrimaryKey(Long jobId);

    int updateByExampleSelective(@Param("record") TbJob record, @Param("example") TbJobExample example);

    int updateByExample(@Param("record") TbJob record, @Param("example") TbJobExample example);

    int updateByPrimaryKeySelective(TbJob record);

    int updateByPrimaryKey(TbJob record);
}