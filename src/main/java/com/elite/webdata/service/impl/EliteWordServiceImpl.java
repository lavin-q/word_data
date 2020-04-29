package com.elite.webdata.service.impl;

import com.elite.webdata.entity.EliteWord;
import com.elite.webdata.mapper.EliteWordMapper;
import com.elite.webdata.service.EliteWordService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class EliteWordServiceImpl implements EliteWordService {

    @Resource
    private EliteWordMapper eliteWordMapper;


    @Override
    public void insert(EliteWord eliteWord) {
        this.eliteWordMapper.insertSelective(eliteWord);
    }

}
