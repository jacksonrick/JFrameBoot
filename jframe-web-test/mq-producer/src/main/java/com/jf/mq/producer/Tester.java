package com.jf.mq.producer;

import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-10-17
 * Time: 17:58
 */
@RestController
public class Tester {

    @Autowired
    private RabbitMQService producer;

    @GetMapping("/send")
    public Integer send() throws InterruptedException {
        for (int i = 1; i <= 50; i++) {
            String str = String.valueOf(i);
            CorrelationData data = new CorrelationData(String.valueOf(i));
            producer.send(RabbitMQService.QUEUE_MSGA, str, data);
        }
        return 1;
    }


    @GetMapping("/send2")
    public Integer send2() throws InterruptedException {
        for (int i = 1; i <= 10; i++) {
            Thread.sleep(1000); // 每1秒发一个
            String str = String.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            producer.senDealy(RabbitMQService.DELAY_ROUTING_KEY, str, 5);
        }
        return 1;
    }


}
