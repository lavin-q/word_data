package com.elite.webdata.rabbitMQ.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description :  消息对列配置参数 //描述
 * @Author : qhm  //作者
 * @Date: 2020-05-30 10:17  //时间
 */
@Component
@ConfigurationProperties(prefix = "rabbit")
@Getter
@Setter
@ToString
public class RabbitProperties {
    private String virtualHost;

    private String username;

    private String password;

    private String host;

    private Integer port;

    private String exchange;
}
