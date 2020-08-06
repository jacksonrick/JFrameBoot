package com.jf;

import com.jf.bean.Bar;
import com.jf.bean.Foo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2020-06-24
 * Time: 14:32
 */
@RestController
public class ProducerController {

    private static final Logger log = LoggerFactory.getLogger(ProducerController.class);

    @Autowired
    private KafkaTemplate<Object, Object> template;

    /**
     * 模拟生产者发送消息
     *
     * @param name
     */
    @GetMapping("/send")
    public void send(String name) {
        log.info("send name: " + name);
        template.send("topicFoo", new Foo(name, 20));
    }

    // 多个消费方法
    @GetMapping("/send/foo")
    public void sendFoo() {
        template.send("foo", new Foo("jack", 30));
    }

    @GetMapping("/send/bar")
    public void sendBar() {
        template.send("bar", new Bar(1, "101"));
    }

    @GetMapping("/send/unknow")
    public void sendUnknow() {
        template.send("bar", "unknow");
    }

    /**
     * 批量-事务
     *
     * @param id
     */
    @GetMapping("/send/batch")
    public void txSend(String names) {
        log.info("##### names : " + names);
        String[] arr = names.split(",");
        template.executeInTransaction(kafkaTemplate -> {
            for (int i = 0; i < arr.length; i++) {
                if (i % 2 == 0) {
                    kafkaTemplate.send("topicTx1", new Foo(arr[i], 1));
                } else {
                    kafkaTemplate.send("topicTx2", new Foo(arr[i], 2));
                }
            }
            return null;
        });
    }

    /**
     * 同时消费和生产【消费端】
     *
     * @param names
     */
    @GetMapping("/send/batch2")
    public void txSend2(String names) {
        log.info("##### names : " + names);
        String[] arr = names.split(",");
        template.executeInTransaction(kafkaTemplate -> {
            for (int i = 0; i < arr.length; i++) {
                kafkaTemplate.send("topicTx3", new Foo(arr[i], 1));
            }
            return null;
        });
    }
}
