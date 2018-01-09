package com.jf.system.mq;

import com.jf.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created with IntelliJ IDEA.
 * Description:消息生产者
 * User: xujunfei
 * Date: 2018-01-09
 * Time: 11:26
 */
//@Service
public class Producer {

    // 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
    @Autowired
    private JmsTemplate jmsTemplate;

    /**
     * 发送文本消息
     */
    public void sendTextMessage(Destination destination, final String message) {
        if (null == destination) {
            destination = jmsTemplate.getDefaultDestination();
        }
        jmsTemplate.convertAndSend(destination, message);
    }

    /**
     * 发送Object序列化
     */
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

    /**
     * 双向队列
     */
    @JmsListener(destination = "out.queue")
    public void consumerMessage(User user) {
        System.out.println("从out.queue队列收到的回复报文为:" + user.getNickname());
    }

    //Destination destination = new ActiveMQQueue("test.queue");
    //producer.sendObjectMessage(destination, ...)
}
