package com.jf.mq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: RabbitMQ消息生产者
 * User: xujunfei
 * Date: 2018-10-17
 * Time: 16:30
 */
@Component
public class RabbitMQService implements InitializingBean {

    private final static Logger log = LoggerFactory.getLogger(RabbitMQService.class);

    public final static String QUEUE_MSGA = "TOPIC.MSG.A";
    public final static String QUEUE_MSGB = "TOPIC.MSG.B";
    public final static String QUEUE_MSGS = "TOPIC.MSGS";
    public final static String QUEUE_DELAY = "QUEUE_DELAY";

    public final static String MY_EXCHANGE = "MY_EXCHANGE";
    public final static String DELAY_EXCHANGE = "DELAY_EXCHANGE";
    public final static String DELAY_ROUTING_KEY = "DELAY_ROUTING_KEY";

    /********************************
     * 队列须先创建（生产者触发或手动）
     * 如更改队列绑定报错，手动删除原队列即可
     ********************************/

    /**
     * 队列A
     *
     * @return
     */
    @Bean
    public Queue queueMsgA() {
        // 持久化队列
        return new Queue(QUEUE_MSGA, true);
    }

    /**
     * 队列B
     *
     * @return
     */
    @Bean
    public Queue queueMsgB() {
        return new Queue(QUEUE_MSGB);
    }


    /**
     * 延迟队列
     * 应用场景：1、用户生成订单之后，需要过一段时间校验订单的支付状态，如果订单仍未支付则需要及时地关闭订单
     * 2、延迟重试。比如消费者从队列里消费消息时失败了，但是想要延迟一段时间后自动重试
     *
     * @return
     */
    @Bean
    public Queue delayQueue() {
        Map<String, Object> params = new HashMap<>();
        // 声明队列里的死信转发到的DLX名称
        params.put("x-dead-letter-exchange", MY_EXCHANGE);
        // 声明这些死信在转发时携带的routing-key名称
        params.put("x-dead-letter-routing-key", QUEUE_MSGB);

        return new Queue(QUEUE_DELAY, true, false, false, params);
    }

    /**
     * Exchange(Topic)
     *
     * @return
     */
    @Bean
    TopicExchange exchange() {
        // 持久化
        return new TopicExchange(MY_EXCHANGE, true, true);
    }

    /**
     * Exchange(Direct) 延迟
     *
     * @return
     */
    @Bean
    DirectExchange delayExchange() {
        return new DirectExchange(DELAY_EXCHANGE);
    }

    /**
     * Binding 消息绑定A
     *
     * @param queueMsgA
     * @param exchange
     * @return
     */
    @Bean
    Binding bindMsgA(Queue queueMsgA, TopicExchange exchange) {
        return BindingBuilder.bind(queueMsgA).to(exchange).with(QUEUE_MSGA);
    }

    /**
     * Binding 消息绑定B
     *
     * @param queueMsgB
     * @param exchange
     * @return
     */
    @Bean
    Binding bindMsgB(Queue queueMsgB, TopicExchange exchange) {
        return BindingBuilder.bind(queueMsgB).to(exchange).with(QUEUE_MSGB);
    }

    /**
     * 延迟队列消息绑定
     *
     * @param delayQueue
     * @param delayExchange
     * @return
     */
    @Bean
    Binding delayBind(Queue delayQueue, DirectExchange delayExchange) {
        return BindingBuilder.bind(delayQueue).to(delayExchange).with(DELAY_ROUTING_KEY);
    }

    // 通配符，接收来自队列TOPIC.MSG.*的所有消息
    /* @Bean
    Binding bindMsgs(Queue queueMsgs, TopicExchange exchange) {
        return BindingBuilder.bind(queueMsgs).to(exchange).with("TOPIC.MSG.#");
    }*/


    //###########################################//

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送者-普通队列
     *
     * @param route  指定队列
     * @param object
     */
    public void send(String route, Object object, CorrelationData data) {
        rabbitTemplate.convertAndSend(MY_EXCHANGE, route, object, data);
    }

    /**
     * 发送者-延迟发送
     *
     * @param route
     * @param object
     * @param timeout 延迟秒数
     */
    public void senDealy(String route, Object object, Integer timeout) {
        rabbitTemplate.convertAndSend(DELAY_EXCHANGE, route, object, message -> {
            // 延迟 5 秒
            message.getMessageProperties().setExpiration(timeout * 1000 + "");
            return message;
        });
    }

    /**
     * 回调
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 用来确认消息是否有送达消息队列
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("消息发送成功:correlationData({}), ack({}), cause({})", correlationData, ack, cause);
            if (!ack) {
                // try to resend msg
            }
        });

        // 若消息找不到对应的Exchange会被触发
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("消息丢失:exchange({}), route({}), replyCode({}), replyText({}), message:{}", exchange, routingKey, replyCode, replyText, message);
        });
    }
}
