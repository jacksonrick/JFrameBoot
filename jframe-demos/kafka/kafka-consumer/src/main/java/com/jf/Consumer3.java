package com.jf;

import com.jf.bean.Foo;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 批量生产和事务
 * User: xujunfei
 * Date: 2020-06-24
 * Time: 15:43
 */
@Component
public class Consumer3 {

    private static final Logger log = LoggerFactory.getLogger(Consumer3.class);

    @Bean
    public NewTopic topicTx1() {
        return TopicBuilder.name("topicTx1").partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic topicTx2() {
        return TopicBuilder.name("topicTx2").partitions(1).replicas(1).build();
    }

    // 批量消费
    @KafkaListener(id = "topicBatchGroup1", topics = "topicTx1")
    public void topicTx1(List<Foo> fooList) {
        log.info("receive batch foo1: " + fooList);
    }

    @KafkaListener(id = "topicBatchGroup2", topics = "topicTx2")
    public void topicTx2(List<Foo> fooList) {
        log.info("receive batch foo2: " + fooList);
    }


    // 同时包含消费和生产
    // read-process-write
    @Autowired
    private KafkaTemplate<String, String> template;

    @Bean
    public NewTopic topicTx3() {
        // TopicBuilder方式
        return TopicBuilder.name("topicTx3").partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic topicTx4() {
        return TopicBuilder.name("topicTx4").partitions(1).replicas(1).build();
    }

    @KafkaListener(id = "topicBatchGroup3", topics = "topicTx3")
    public void topicTx3(List<Foo> fooList) {
        log.info("receive batch foo3: " + fooList);
        for (Foo foo : fooList) {
            template.send("topicTx4", foo.getName());
        }
    }

    @KafkaListener(id = "topicBatchGroup4", topics = "topicTx4")
    public void topicTx4(List<String> fooList) {
        log.info("receive batch foo4: " + fooList);
    }

}
