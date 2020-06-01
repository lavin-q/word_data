package com.elite.webdata.rabbitMQ.utils;

import com.elite.webdata.rabbitMQ.config.RabbitProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Description : RabbitMQ工具类  //描述
 * @Author : qhm  //作者
 * @Date: 2020-05-30 10:15  //时间
 */
//@Component
public class RabbitMQUtil{

    //@Autowired
    //private RabbitProperties rabbitProperties;

    //private SpringUtil springUtil;


    public Connection getConnection(RabbitProperties rabbitProperties) throws Exception {
        if(null==rabbitProperties){
            rabbitProperties = SpringUtil.getBean(RabbitProperties.class);
        }
        System.out.println("创建连接！");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitProperties.getHost());
        factory.setPort(rabbitProperties.getPort());
        factory.setVirtualHost(rabbitProperties.getVirtualHost());
        factory.setUsername(rabbitProperties.getUsername());
        factory.setPassword(rabbitProperties.getPassword());
        return factory.newConnection();
    }

    public Channel getChannel(Connection connection,RabbitProperties rabbitProperties) throws Exception {
        if(null == connection){
            connection = getConnection(rabbitProperties);
        }
        return connection.createChannel();
    }
}
