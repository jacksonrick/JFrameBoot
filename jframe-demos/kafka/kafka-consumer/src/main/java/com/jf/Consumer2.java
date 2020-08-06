package com.jf;

import com.jf.bean.Bar;
import com.jf.bean.Foo;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description: 多队列分发处理器
 * User: xujunfei
 * Date: 2020-06-24
 * Time: 14:53
 */
@Component
@KafkaListener(id = "mutilGroup", topics = {"foo", "bar"})
public class Consumer2 {

    private static final Logger log = LoggerFactory.getLogger(Consumer2.class);

    // 定义不同bean类型的队列
    @Bean
    public NewTopic foo() {
        return new NewTopic("foo", 1, (short) 1);
    }

    @Bean
    public NewTopic bar() {
        return new NewTopic("bar", 1, (short) 1);
    }

    // 定义各个bean的处理器
    @KafkaHandler
    public void foo(Foo foo) {
        log.info("mutil received foo: " + foo);
    }

    @KafkaHandler
    public void foo(Bar bar) {
        log.info("mutil received bar: " + bar);
    }

    // 如未找到对应类，则使用默认处理器
    @KafkaHandler(isDefault = true)
    public void foo(Object obj) {
        log.info("mutil received unknow: " + obj);
    }

}
