package com.jf.mq.consumer1;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
            log.info("A Receiver：" + msg + "，消费错误：" + e.getMessage());

            // 必须抛出异常，重试才会生效 listener.simple.retry
            // throw new RuntimeException("消费错误: " + msg);

            if (message.getMessageProperties().getRedelivered()) {
                log.info("消息已重复处理失败,拒绝再次接收...");

                // 发到另一个队列（可以是专门接收失败消息的队列）
                channel.basicPublish(MY_EXCHANGE, QUEUE_MSGB, null, ("msg: " + msg + " error").getBytes());
                // 确认消息消费失败
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);

                // 拒绝消息
                // channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                log.info("消息即将再次返回队列处理...");
                // requeue为是否重新回到队列
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }

    }

    /* // 测试重试次数
    @RabbitListener(queues = QUEUE_MSGA, containerFactory = "rabbitListenerContainerFactory")
    public void process(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("msg：" + msg + " ,channelno: " + channel.getChannelNumber() + " ,prop: " + message.getMessageProperties().toString());
        try {
            // do something
            // 通知 MQ 消息已被成功消费
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            long retryCount = getRetryCount(message.getMessageProperties());
            log.info("msg：" + msg + "，消费错误：" + e.getMessage() + "，retryCount:" + retryCount);
            if (retryCount > 3) {
                // 重试次数大于3次，则自动加入到失败队列
                channel.basicPublish(IConstant.EXCHANGE_FAIL, IConstant.QUEUE_FAIL, overrideProperties(message.getMessageProperties()), message.getBody());
            } else {
                // 重试次数小于3，则加入到重试队列，n秒后重试
                channel.basicPublish(IConstant.EXCHANGE_RETRY, IConstant.QUEUE_RETRY, overrideProperties(message.getMessageProperties()), message.getBody());
            }

            // 由于开启了手动确认模式，所以这里还需要通知MQ消息已被消费，否则队列任务会递增
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }*/

    /**
     * MessageProperties转换为AMQP.BasicProperties
     *
     * @param properties
     * @return
     */
    protected AMQP.BasicProperties overrideProperties(MessageProperties properties) {
        return new AMQP.BasicProperties(
                properties.getContentType(),
                properties.getContentEncoding(),
                properties.getHeaders(),
                2,
                properties.getPriority(),
                properties.getCorrelationId(),
                properties.getReplyTo(),
                properties.getExpiration(),
                properties.getMessageId(),
                properties.getTimestamp(),
                properties.getType(),
                properties.getUserId(),
                properties.getAppId(),
                properties.getClusterId()
        );
    }

    /**
     * 获取重试次数
     *
     * @param properties
     * @return
     */
    protected Long getRetryCount(MessageProperties properties) {
        Long retryCount = 0L;
        try {
            Map<String, Object> headers = properties.getHeaders();
            if (headers != null) {
                if (headers.containsKey("x-death")) {
                    List<Map<String, Object>> deaths = (List<Map<String, Object>>) headers.get("x-death");
                    if (deaths.size() > 0) {
                        Map<String, Object> death = deaths.get(0);
                        retryCount = (Long) death.get("count");
                    }
                }
            }
        } catch (Exception e) {
        }

        return retryCount;
    }
}
