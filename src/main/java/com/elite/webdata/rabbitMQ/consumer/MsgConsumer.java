package com.elite.webdata.rabbitMQ.consumer;

import com.elite.webdata.rabbitMQ.config.RabbitProperties;
import com.elite.webdata.rabbitMQ.utils.RabbitMQUtil;
import com.rabbitmq.client.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description : 书籍消息消费者  //描述
 * @Author : qhm  //作者
 * @Date: 2020-06-01 15:18  //时间
 */
//@Component
public class MsgConsumer {

    @Resource(name = "rabbitMQUtil")
    private RabbitMQUtil rabbitMQUtil;
    @Resource
    private RabbitProperties rabbitProperties;

    //消费端监听的是死信队列，如果conusmer收到了消息，表明死信队列里面有消息了
    private static final String QUEUE_NAME = "dlx.queue";

    @PostConstruct
    public void testDeadQueueComsumer() throws Exception {
        // 创建信道
        Channel channel = rabbitMQUtil.getChannel(null,rabbitProperties);

        System.out.println("消费者启动 ..........");

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.err.println("死信队列接收到消息：" + new String(body));
                System.err.println("deliveryTag:" + envelope.getDeliveryTag());
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        channel.basicConsume(QUEUE_NAME, consumer);
        //TimeUnit.SECONDS.sleep(10000000L);
    }

    @PostConstruct
    public void comsumer() throws Exception {
        Connection connection = rabbitMQUtil.getConnection(rabbitProperties);
        // 创建信道
        Channel channel = rabbitMQUtil.getChannel(connection,rabbitProperties);
        String exchangeName = "test_ack_exchange";
        String exchangeType="topic";
        final String queueName = "test_ack_queue";
        String routingKey = "ack.#";

        //死信队列配置
        String deadExchangeName = "dead_exchange";
        String deadQueueName = "dead_queue";
        String deadRoutingKey = "#";

        //如果需要将死信消息路由
        Map<String,Object> arguments = new HashMap<String, Object>();
        arguments.put("x-dead-letter-exchange",deadExchangeName);

        channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);
        channel.queueDeclare(queueName,false,false,false,arguments);
        channel.queueBind(queueName,exchangeName,routingKey);

        //死信队列绑定配置  ----------------
        channel.exchangeDeclare(deadExchangeName,exchangeType,true,false,false,null);
        channel.queueDeclare(deadQueueName,true,false,false,null);
        channel.queueBind(deadQueueName,deadExchangeName,deadRoutingKey);

        System.out.println("consumer启动 .....");

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try{
                    Thread.sleep(2000);
                }catch (Exception e){

                }
                Integer num = (Integer)properties.getHeaders().get("num");
                if(num==0){
                    //未被ack的消息，并且requeue=false。即nack的 消息不再被退回队列而成为死信队列
                    channel.basicNack(envelope.getDeliveryTag(),false,false);
                    String message = new String(body, "UTF-8");
                    System.out.println("consumer端的Nack消息是： " + message);
                }else {
                    channel.basicAck(envelope.getDeliveryTag(),false);
                    String message = new String(body, "UTF-8");
                    System.out.println("consumer端的ack消息是： " + message);
                }
            }
        };
        //消息要能重回队列，需要设置autoAck的属性为false，即在回调函数中进行手动签收
        channel.basicConsume(queueName,false,consumer);
    }
}
