package com.elite.webdata.rabbitMQ.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description : RabbitMQ  Config Class  //描述
 * @Author : qhm  //作者
 * @Date: 2020-08-24 14:06  //时间
 */
//@Configuration
//@Component
public class RabbitConfig{

    @Resource
    private RabbitAdmin rabbitAdmin;

    /**
     * 派单队列
     */
    public static final String ORDER_DIC_QUEUE = "order_dic_queue";
    /**
     * 补单对接
     */
    public static final String ORDER_CREATE_QUEUE = "order_create_queue";
    /**
     * 订单交换机
     */
    private static final String ORDER_EXCHANGE_NAME = "order_exchange_name";

    /**
     * 定义派单队列
     */
    @Bean
    public Queue directOrderDicQueue() {
        return new Queue(ORDER_DIC_QUEUE, true, false, false);
    }

    /**
     * 定义补派单队列
     */
    @Bean
    public Queue directCreateOrderQueue() {
        return new Queue(ORDER_CREATE_QUEUE, true, false, false);
    }

    /**
     * 定义订单交换机
     */
    @Bean
    public DirectExchange directOrderExchange() {
        return new DirectExchange(ORDER_EXCHANGE_NAME, true, false);
    }

    /**
     * 派单队列与交换机绑定
     */
    @Bean
    public Binding bindingExchangeOrderDicQueue() {
        return BindingBuilder.bind(directOrderDicQueue()).to(directOrderExchange()).with("orderRoutingKey");
    }

    /**
     * 补单队列与交换机绑定
     */
    @Bean
    Binding bindingExchangeCreateOrder() {
        return BindingBuilder.bind(directCreateOrderQueue()).to(directOrderExchange()).with("orderRoutingKey");
    }

    //创建初始化RabbitAdmin对象
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        // 只有设置为 true，spring 才会加载 RabbitAdmin 这个类
        rabbitAdmin.setAutoStartup(true);
        //rabbitAdmin.initialize();
        return rabbitAdmin;
    }

    //创建交换机和对列
    @Bean
    public void createExchangeQueue() {
        rabbitAdmin.declareExchange(directOrderExchange());
        rabbitAdmin.declareQueue(directOrderDicQueue());
        rabbitAdmin.declareQueue(directCreateOrderQueue());
    }

}
