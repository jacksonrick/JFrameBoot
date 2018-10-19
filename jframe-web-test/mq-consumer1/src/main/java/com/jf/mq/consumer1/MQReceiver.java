package com.jf.mq.consumer1;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-10-17
 * Time: 17:47
 */
@Component
public class MQReceiver {

    private final static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    public final static String QUEUE_MSGA = "TOPIC.MSG.A";
    public final static String QUEUE_MSGB = "TOPIC.MSG.B";

    public final static String MY_EXCHANGE = "MY_EXCHANGE";

    /**
     * 消费者A
     *
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = QUEUE_MSGA, containerFactory = "rabbitListenerContainerFactory")
    public void process(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("A Receiver: " + msg + " ,channelno: " + channel.getChannelNumber());
        try {
            Thread.sleep(2000); // 模拟处理时间
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            if ("10".equals(msg)) {
                System.out.println(1 / 0); // 模拟出错
            } else {
                // 通知 MQ 消息已被成功消费
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        } catch (Exception e) {
            log.info("A Receiver错误：" + e.getMessage());

            if (message.getMessageProperties().getRedelivered()) {
                log.info("消息已重复处理失败,拒绝再次接收...");
                // 拒绝消息
                // channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);

                // 发到另一个队列（可以是专门接收失败消息的队列）
                channel.basicPublish(MY_EXCHANGE, QUEUE_MSGB, null, ("msg: " + msg + " error").getBytes());
            } else {
                log.info("消息即将再次返回队列处理...");
                // requeue为是否重新回到队列
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }

    }
}
