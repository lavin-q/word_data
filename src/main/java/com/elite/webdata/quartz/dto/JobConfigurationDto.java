package com.elite.webdata.quartz.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;

/**
 * @Description : job配置实体类  //描述
 * @Author : qhm  //作者
 * @Date: 2021-01-10 13:24  //时间
 */
@Getter
@Setter
public class JobConfigurationDto {

    //job参数配置
    @Valid
    private JobDetailDto jobDetail;

    //启动项参数配置
    @Valid
    private JobTriggerDto jobTrigger;
}
