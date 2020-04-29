package com.elite.webdata.mapper;

import com.elite.webdata.entity.EliteWord;
import com.elite.webdata.entity.EliteWordExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EliteWordMapper {
    int countByExample(EliteWordExample example);

    int deleteByExample(EliteWordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(EliteWord record);

    int insertSelective(EliteWord record);

    List<EliteWord> selectByExample(EliteWordExample example);

    EliteWord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") EliteWord record, @Param("example") EliteWordExample example);

    int updateByExample(@Param("record") EliteWord record, @Param("example") EliteWordExample example);

    int updateByPrimaryKeySelective(EliteWord record);

    int updateByPrimaryKey(EliteWord record);
}