package com.jf;

import com.jf.bean.Foo;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.backoff.FixedBackOff;

/**
 * Created with IntelliJ IDEA.
 * Description: 普通队列和死信队列
 * User: xujunfei
 * Date: 2020-06-24
 * Time: 11:38
 */
@Component
public class Consumer1 {

    private static final Logger log = LoggerFactory.getLogger(Consumer1.class);

    /**
     * 异常捕捉
     * <p>发生异常后会发送到死信队列，如：topic.DLT</p>
     *
     * @param template
     * @return
     */
    @Bean
    public SeekToCurrentErrorHandler errorHandler(KafkaTemplate<Object, Object> template) {
        return new SeekToCurrentErrorHandler(
                new DeadLetterPublishingRecoverer(template), new FixedBackOff(10000l, 3)); //每10秒重试，共3次
    }

    // 定义两个队列: topic和topic.DLT
    @Bean
    public NewTopic topic() {
        return new NewTopic("topicFoo", 3, (short) 3);
    }

    @Bean
    public NewTopic dlt() {
        return new NewTopic("topicFoo.DLT", 1, (short) 1);
    }

    /**
     * 消费者
     * <p>消费失败会被异常方法捕捉，并发送到死信队列</p>
     *
     * @param foo
     */
    @KafkaListener(id = "fooGroup", topics = "topicFoo")
    public void listen(Foo foo) {
        if ("fail".equals(foo.getName())) {
            throw new RuntimeException("received failed");
        }
        log.info("received foo: " + foo);
    }

    /**
     * 死信队列
     *
     * @param foo
     */
    @KafkaListener(id = "fooGroupDlt", topics = "topicFoo.DLT")
    public void listenDlt(Foo foo) {
        log.warn("received dlt foo: " + foo);
    }

}
