package com.elite.webdata.quartz.dto;

import com.elite.webdata.quartz.config.group.GroupA;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;
import java.io.Serializable;

/**
 * @Description :   //描述
 * @Author : qhm  //作者
 * @Date: 2021-01-10 13:39  //时间
 */
@Getter
@Setter
@ToString
public class JobDetailDto implements Serializable {

    @NotBlank(message = "jobClassName cannot be null")
    private String jobClassName;

    @NotBlank(message = "jobDataKey cannot be null")
    private String jobDataKey;

    @NotBlank(message = "jobDataValue cannot be null")
    private String jobDataValue;

    @NotBlank(message = "jobName cannot be null",groups = {GroupA.class, Default.class})
    private String jobName;

    @NotBlank(message = "jobGroupName cannot be null",groups = {GroupA.class, Default.class})
    private String jobGroupName;
}
