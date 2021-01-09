package com.elite.webdata.rabbitMQ.config;

import com.elite.webdata.rabbitMQ.utils.RabbitMQUtil;
import com.elite.webdata.rabbitMQ.utils.SpringUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description : 自定义类添加到Spring容器  //描述
 * @Author : qhm  //作者
 * @Date: 2020-05-30 11:11  //时间
 */
@Configuration
public class BeanConfig {

    @Bean("springYtil")
    public SpringUtil getSpringUtil(){
        return new SpringUtil();
    }

    @Bean("rabbitMQUtil")
    public RabbitMQUtil getRabbitMQUtil(){
        return new RabbitMQUtil();
    }
}
