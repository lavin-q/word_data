package com.elite.webdata.rabbitMQ.producer;

import com.elite.webdata.rabbitMQ.config.RabbitProperties;
import com.elite.webdata.rabbitMQ.utils.RabbitMQUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description : 书籍消息生产者  //描述
 * @Author : qhm  //作者
 * @Date: 2020-06-01 15:07  //时间
 */
//@Component
public class MsgProducer {

    @Resource(name = "rabbitMQUtil")
    private RabbitMQUtil rabbitMQUtil;
    @Resource
    private RabbitProperties rabbitProperties;

    //@PostConstruct
    public Object produceMsg() {
        try {
            Connection connection = rabbitMQUtil.getConnection(rabbitProperties);
            Channel channel = rabbitMQUtil.getChannel(connection, rabbitProperties);
            int i = 0;
            List<String> msgList = new ArrayList<>();
            while (i < 10) {
                String msg = "消息序列号：" + UUID.randomUUID().toString() + "；编号" + i;
                msgList.add(msg);
                i++;
            }
            rabbitMQUtil.sendBatchMessageToExchange(channel, rabbitProperties.getExchange(),
                    rabbitProperties.getQueueName(), rabbitProperties.getRoutingKey(), msgList);
            rabbitMQUtil.closeChannel(channel);
            rabbitMQUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "消息发送成功";
    }

    //@PostConstruct
    public Object testDeadQueue() throws Exception {
        Connection connection = rabbitMQUtil.getConnection(rabbitProperties);
        Channel channel = rabbitMQUtil.getChannel(connection, rabbitProperties);

        String orderExchangeName = "order_exchange";
        String orderQueueName = "order_queue";
        String orderRoutingKey = "order.#";
        Map<String, Object> arguments = new HashMap<String, Object>(16);

        //死信队列配置  ----------------
        String dlxExchangeName = "dlx.exchange";
        String dlxQueueName = "dlx.queue";
        String dlxRoutingKey = "#";

        // 为队列设置队列交换器
        arguments.put("x-dead-letter-exchange", dlxExchangeName);
        // 设置队列中的消息 10s 钟后过期
        arguments.put("x-message-ttl", 10000);

        //正常的队列绑定
        channel.exchangeDeclare(orderExchangeName, "topic", true, false, null);
        channel.queueDeclare(orderQueueName, true, false, false, arguments);
        channel.queueBind(orderQueueName, orderExchangeName, orderRoutingKey);

        String message = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 创建订单.";

        // 创建死信交换器和队列
        channel.exchangeDeclare(dlxExchangeName, "topic", true, false, null);
        channel.queueDeclare(dlxQueueName, true, false, false, null);
        channel.queueBind(dlxQueueName, dlxExchangeName, orderRoutingKey);

        channel.basicPublish(orderExchangeName, "order.save", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        System.err.println("消息发送完成......");
        return null;
    }

    @PostConstruct
    public void producer() throws Exception {
        Connection connection = rabbitMQUtil.getConnection(rabbitProperties);
        Channel channel = rabbitMQUtil.getChannel(connection, rabbitProperties);
        String exchangeName = "test_ack_exchange";
        String routingKey = "ack.save";
        channel.exchangeDeclare(exchangeName, "topic", true, false, null);
        for (int i = 0; i < 5; i++) {
            Map<String, Object> headers = new HashMap<String, Object>();
            headers.put("num", i);
            AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().deliveryMode(2).contentEncoding("UTF_8")
                    .headers(headers)
                    .build();
            String message = "hello this is ack message ....." + i;
            System.out.println(message);
            channel.basicPublish(exchangeName, routingKey, true, properties, message.getBytes());
        }
        channel.close();
        connection.close();
    }
}
