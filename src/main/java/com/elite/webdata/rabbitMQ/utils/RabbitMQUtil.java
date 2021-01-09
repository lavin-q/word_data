package com.elite.webdata.rabbitMQ.utils;

import com.elite.webdata.rabbitMQ.config.RabbitProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Description : RabbitMQ工具类  //描述
 * @Author : qhm  //作者
 * @Date: 2020-05-30 10:15  //时间
 */
//@Component
@Slf4j
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

    /**
     * 发送单条消息到消息队列
     *
     * @param channel   连接
     * @param queueName 队列名称
     * @param message   消息体
     * @return true(成功)/false(失败)
     */
    public  boolean sendOneMessageToQueue(Channel channel, String queueName, String message) {
        boolean flag = false;
        try {
            channel.queueDeclare(queueName, false, false, false, null);
            channel.confirmSelect();
            channel.basicPublish("", queueName, null, (message).getBytes(StandardCharsets.UTF_8));
            if (channel.waitForConfirms()) {
                flag = true;
                log.info("消息发送成功:" + message);
            }
        } catch (Exception e) {
            log.info("消息发送失败:" + message);
        }
        return flag;
    }


    /**
     * 批量消息发送到队列
     * @param channel 连接
     * @param queueName 队列名
     * @param message 消息
     * @return true(成功)/false(失败)
     */
    public  boolean sendBatchMessageToQueue(Channel channel, String queueName, List<String> message) {
        final boolean[] flag = {false};
        try {
            channel.queueDeclare(queueName, false, false, false, null);
            channel.confirmSelect();
            for (String msg : message) {
                channel.basicPublish("", queueName, null, msg.getBytes(StandardCharsets.UTF_8));
            }
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) {
                    flag[0] = true;
                    System.out.println(deliveryTag+":"+message);
                    log.info("消息发送成功:" + message);
                }
                @Override
                public void handleNack(long deliveryTag, boolean multiple) {
                    //TODO 批量消息中存在单挑消息发送失败的情况如何处理？
                    //放入redis中，定时抓取消息重新发送?
                    System.out.println(deliveryTag+":"+message);
                    log.info("消息发送失败:" + message);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag[0];
    }

    /**
     *
     * @param channel
     * @param exchangeName
     * @param queueName
     * @param routingKey
     * @param message
     * @return
     */
    public boolean sendBatchMessageToExchange(Channel channel, String exchangeName,String queueName,String routingKey, List<String> message) {
        final boolean[] flag = {false};
        try {
            channel.exchangeDeclare(exchangeName,"topic", true, false, null);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, routingKey);
            channel.confirmSelect();
            for (String msg : message) {
                channel.basicPublish(exchangeName, routingKey, null, msg.getBytes(StandardCharsets.UTF_8));
            }
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) {
                    flag[0] = true;
                    System.out.println(deliveryTag+":"+message);
                    log.info("消息发送成功:" + message);
                }
                @Override
                public void handleNack(long deliveryTag, boolean multiple) {
                    //TODO 批量消息中存在单挑消息发送失败的情况如何处理？
                    //放入redis中，定时抓取消息重新发送?
                    System.out.println(deliveryTag+":"+message);
                    log.info("消息发送失败:" + message);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag[0];
    }



    /**
     * 关闭连接
     * @param conn 连接
     * @return
     */
    public  boolean closeConnection(Connection conn) {
        boolean flag = false;
        if(conn == null){
            flag = true;
        }else{
            try {
                conn.close();
                flag = true;
            } catch (IOException e) {
                log.error("关闭连接失败{}",e.getMessage());
                System.out.println("关闭连接失败{"+e.getMessage()+"}");
                flag = false;
            }

        }
        return flag;
    }

    public boolean closeChannel(Channel channel){
        boolean flag = false;
        if(channel == null){
            flag = true;
        }else{
            try {
                channel.close();
                flag = true;
            } catch (Exception e) {
                log.error("关闭通道失败{}",e.getMessage());
                System.out.println("关闭通道失败{"+e.getMessage()+"}");
                flag = false;
            }
        }
        return flag;
    }
}
