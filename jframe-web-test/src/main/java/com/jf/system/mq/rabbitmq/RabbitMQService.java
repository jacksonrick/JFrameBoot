package com.jf.system.mq.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * Description: rabbitmq service
 * User: xujunfei
 * Date: 2018-05-02
 * Time: 10:47
 */
@Configuration
@ConditionalOnProperty(name = "app.rabbitmq.enabled", havingValue = "true")
public class RabbitMQService {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter()); // 设置序列化类
        return template;
    }

    @Bean("rabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter()); // 设置序列化类
        factory.setPrefetchCount(50); // 每个消费者获取最大投递数量 默认50
        factory.setConcurrentConsumers(10); // 消费者数量 默认10
        return factory;
    }

    final static String msgA = "topic.msg.a";
    final static String msgB = "topic.msg.b";
    final static String msgs = "topic.msgs";

    // Queue
    @Bean
    public Queue queueMsgA() {
        return new Queue(RabbitMQService.msgA);
    }

    @Bean
    public Queue queueMsgB() {
        return new Queue(RabbitMQService.msgB);
    }

    @Bean
    public Queue queueMsgs() {
        return new Queue(RabbitMQService.msgs);
        // return new Queue(RabbitMQService.msgs, true); // 持久化
    }


    // Exchange (Topic) 绑定队列
    @Bean
    TopicExchange exchange() {
        return new TopicExchange("exchange");
        // return new TopicExchange("exchange", true, true);
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
    private RabbitTemplate amqpTemplate;

    /**
     * 发送者
     *
     * @param topic  指定队列
     * @param object
     */
    public void send(String topic, Object object) {
        this.amqpTemplate.convertAndSend("exchange", topic, object);
    }

}
