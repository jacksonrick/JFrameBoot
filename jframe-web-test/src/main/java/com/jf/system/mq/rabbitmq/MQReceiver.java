package com.jf.system.mq.rabbitmq;

import com.jf.database.model.User;
import com.jf.json.JacksonUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description: RabbitMQ消费者
 * User: xujunfei
 * Date: 2018-05-02
 * Time: 10:52
 */
@Component
@ConditionalOnBean(RabbitMQService.class)
public class MQReceiver {

    @RabbitListener(queues = "topic.msg.a", containerFactory = "rabbitListenerContainerFactory")
    public void process(String message) {
        System.out.println("A Receiver: " + message);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            User user = JacksonUtil.jsonToBean(message, User.class);
            if (user.getId() == 30) {
                System.out.println(1 / 0);
            }
        } catch (Exception e) {
            System.out.println("消费错误：" + e.getMessage());
        }
    }

    @RabbitListener(queues = "topic.msg.b", containerFactory = "rabbitListenerContainerFactory")
    public void process2(String str) {
        System.out.println("B Receiver: " + str);
    }

    @RabbitListener(queues = "topic.msgs", containerFactory = "rabbitListenerContainerFactory")
    public void process3(Object obj) {
        Message message = (Message) obj;
        System.out.println("All Receivers: " + message.getBody() + " ,type: " + message.getMessageProperties().getContentType());
    }

}
