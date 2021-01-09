package com.elite.webdata.rabbitMQ.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description : 生产者  //描述
 * @Author : qhm  //作者
 * @Date: 2020-08-24 14:21  //时间
 */
//@Component
@Slf4j
public class SpringBootMsgProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;


}
