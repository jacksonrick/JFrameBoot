package com.jf.system.mq.activemq;

/*
import com.jf.database.model.User;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
@ConditionalOnProperty(name = "app.activemq.enabled", havingValue = "true")
public class ActiveMQService {

    // 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
    @Autowired
    private JmsTemplate jmsTemplate;

    *//**
     * 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
     *//*
    @JmsListener(destination = "test.queue")
    @SendTo("out.queue")
    public User receiveQueue(User user) {
        System.out.println("Consumer收到的报文为:" + user.toString());
        User user2 = new User(10000L);
        user2.setNickname(user.getNickname());
        return user2;
    }

    *//**
     * 双向队列
     *//*
    @JmsListener(destination = "out.queue")
    public void consumerMessage(User user) {
        System.out.println("从out.queue队列收到的回复报文为:" + user.getNickname());
    }


    *//**
     * 发送文本消息
     *//*
    public void sendTextMessage(Destination destination, final String message) {
        if (null == destination) {
            destination = jmsTemplate.getDefaultDestination();
        }
        jmsTemplate.convertAndSend(destination, message);
    }

    *//**
     * 发送Object序列化
     *//*
    public void sendObjectMessage(Destination destination, final User user) {
        if (null == destination) {
            destination = jmsTemplate.getDefaultDestination();
        }
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(user);
            }
        });
    }

    //Destination destination = new ActiveMQQueue("test.queue");
    //User user = new User(1000l);
    //producer.sendObjectMessage(destination, user);

}
*/
