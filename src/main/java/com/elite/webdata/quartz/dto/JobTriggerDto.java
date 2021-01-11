package com.elite.webdata.quartz.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Description :   //描述
 * @Author : qhm  //作者
 * @Date: 2021-01-10 13:40  //时间
 */
@Getter
@Setter
@ToString
public class JobTriggerDto implements Serializable {

    @NotBlank(message = "jobDataKey cannot be null")
    private String jobDataKey;

    @NotBlank(message = "triggerGroupName cannot be null")
    private String triggerGroupName;

    @NotBlank(message = "cronExpression cannot be null")
    private String cronExpression;

}
