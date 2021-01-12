package com.elite.webdata.quartz.controller;

import com.elite.webdata.quartz.config.group.GroupA;
import com.elite.webdata.quartz.dto.JobConfigurationDto;
import com.elite.webdata.quartz.dto.JobDetailDto;
import com.elite.webdata.quartz.service.QuartzService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @Description : QuartzController  //描述
 * @Author : qhm  //作者
 * @Date: 2021-01-10 13:49  //时间
 */
@Log4j2
@RestController
@RequestMapping("/quartz")
public class QuartzJobController {

    @Resource(name = "quartzService")
    private QuartzService quartzService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addQuartzJob(@RequestBody @Validated JobConfigurationDto jobConfigurationDto, BindingResult result) {
        StringBuilder errorMessage = new StringBuilder();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append(",");
                System.out.println(error.getDefaultMessage());
            }
            log.info(errorMessage.toString());
            return errorMessage.toString();
        }
        Object o = this.quartzService.addQuartzCronJob(jobConfigurationDto);
        return o.toString();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Object removeQuartzJob(@RequestBody @Validated({GroupA.class}) JobDetailDto JobDetailDto, BindingResult result) {
        StringBuilder errorMessage = new StringBuilder();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append(",");
                System.out.println(error.getDefaultMessage());
            }
            log.info(errorMessage.toString());
            return errorMessage.toString();
        }
        Object o = this.quartzService.removeQuartzCronJob(JobDetailDto.getJobName(), JobDetailDto.getJobGroupName());
        return o.toString();
    }

}
