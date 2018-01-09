package com.jf.system.mq;

import com.jf.model.User;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description:消息消费者
 * User: xujunfei
 * Date: 2018-01-09
 * Time: 11:26
 */
//@Component
public class Consumer {

    /**
     * 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
     */
    @JmsListener(destination = "test.queue")
    @SendTo("out.queue")
    public User receiveQueue(User user) {
        System.out.println("Consumer收到的报文为:" + user.toString());
        User user2 = new User(1000l);
        user2.setNickname(user.getNickname());
        return user2;
    }

}
