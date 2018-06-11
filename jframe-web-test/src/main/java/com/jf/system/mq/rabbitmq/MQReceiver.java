package com.jf.system.mq.rabbitmq;

import com.jf.database.model.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description: 接收者
 * User: xujunfei
 * Date: 2018-05-02
 * Time: 10:52
 */
@Component
@ConditionalOnBean(RabbitMQService.class)
public class MQReceiver {

    // 接收指定队列消息
    @Component
    @RabbitListener(queues = "topic.msg.a", containerFactory = "rabbitListenerContainerFactory")
    class Receiver1 {

        @RabbitHandler
        public void process(User user) {
            System.out.println("A Receiver: " + user.getNickname());
        }

    }

    @Component
    @RabbitListener(queues = "topic.msg.b")
    class Receiver2 {

        @RabbitHandler
        public void process(String str) {
            System.out.println("B Receiver: " + str);
        }

    }

    @Component
    @RabbitListener(queues = "topic.msgs")
    class Receiver3 {

        @RabbitHandler
        public void process(Object obj) {
            Message message = (Message) obj;
            System.out.println("All Receivers: " + message.getBody() + " ,type: " + message.getMessageProperties().getContentType());
        }

    }

}
