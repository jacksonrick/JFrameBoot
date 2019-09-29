package com.jf.mq.consumer2;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public final static String QUEUE_MSGB = "TOPIC.MSG.B";

    /**
     * 消费者B
     *
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = QUEUE_MSGB, containerFactory = "rabbitListenerContainerFactory")
    public void process(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("B Receiver: " + msg + " ,channelno: " + channel.getChannelNumber());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
