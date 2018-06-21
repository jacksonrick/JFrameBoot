package com.jf.system.mq.rabbitmq;

import com.jf.database.model.User;
import com.jf.json.JSONUtils;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * Description: rabbitmq消息生产者
 * User: xujunfei
 * Date: 2018-05-02
 * Time: 10:47
 */
@Configuration
@ConditionalOnProperty(name = "app.rabbitmq.enabled", havingValue = "true")
public class RabbitMQService implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback, InitializingBean {

    /*@Bean
    @Primary
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }*/

    /*@Bean("rabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //factory.setMessageConverter(new Jackson2JsonMessageConverter()); // 设置序列化类
        factory.setPrefetchCount(50); // 每个消费者获取最大投递数量 默认50
        factory.setConcurrentConsumers(10); // 消费者数量 默认10
        return factory;
    }*/

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(queueMsgA());
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 设置确认模式 手工确认
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                byte[] body = message.getBody();
                System.out.println("receive msg : " + new String(body));
                boolean success = true;
                long id = 0;
                try {
                    User user = JSONUtils.toBean(new String(body), User.class);
                    id = user.getId();
                    if (id == 31) {
                        success = false;
                    }
                } finally {
                    if (success) {
                        System.out.println("ACK-" + id);
                        // 确认消息成功消费
                        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    } else {
                        System.out.println("NACK-" + id);
                        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                    }
                }
            }
        });
        return container;
    }

    final static String msgA = "topic.msg.a";
    final static String msgB = "topic.msg.b";
    final static String msgs = "topic.msgs";

    // Queue
    @Bean
    public Queue queueMsgA() {
        // 持久化
        return new Queue(RabbitMQService.msgA, true);
    }

    @Bean
    public Queue queueMsgB() {
        return new Queue(RabbitMQService.msgB);
    }

    @Bean
    public Queue queueMsgs() {
        return new Queue(RabbitMQService.msgs);
    }


    // Exchange (Topic) 绑定队列
    @Bean
    TopicExchange exchange() {
        return new TopicExchange("exchange", true, true);
    }

    @Bean
    Binding bindMsgA(Queue queueMsgA, TopicExchange exchange) {
        return BindingBuilder.bind(queueMsgA).to(exchange).with("topic.msg.a");
    }

    @Bean
    Binding bindMsgB(Queue queueMsgB, TopicExchange exchange) {
        return BindingBuilder.bind(queueMsgB).to(exchange).with("topic.msg.b");
    }

    @Bean
    Binding bindMsgs(Queue queueMsgs, TopicExchange exchange) {
        // 通配符，接收来自队列topic.msg.*的所有消息
        return BindingBuilder.bind(queueMsgs).to(exchange).with("topic.msg.#");
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 发送者
     *
     * @param topic  指定队列
     * @param object
     */
    public void send(String topic, Object object) {
        rabbitTemplate.convertAndSend("exchange", topic, object);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println(" 回调id:" + correlationData);
        if (ack) {
            System.out.println("消息成功消费");
        } else {
            System.out.println("消息消费失败:" + cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("消息发送失败: " + new String(message.getBody()));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        rabbitTemplate.setConfirmCallback(this::confirm);
        rabbitTemplate.setReturnCallback(this::returnedMessage);
    }
}
