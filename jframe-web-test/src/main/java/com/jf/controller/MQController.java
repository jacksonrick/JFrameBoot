package com.jf.controller;

import com.jf.common.BaseController;
import com.jf.database.model.User;
import com.jf.entity.ResMsg;
import com.jf.entity.enums.ResCode;
import com.jf.json.JSONUtils;
import com.jf.system.mq.rabbitmq.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-06-21
 * Time: 13:57
 */
@Controller
public class MQController extends BaseController {

    //@Autowired(required = false)
    //private ActiveMQService producer; // activemq
    @Autowired(required = false)
    private RabbitMQService producer2; // rabbitmq

    /**
     * activemq send
     * 弃用，选用RabbitMQ
     */
    /*@RequestMapping("/mq_send")
    @ResponseBody
    public ResMsg mq_send() {
        Destination destination = new ActiveMQQueue("test.queue");
        User user = new User(1000l);
        user.setNickname("hahha");
        producer.sendObjectMessage(destination, user);
        return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg());
    }*/

    /**
     * rabbitmq send
     */
    @RequestMapping("/mq_send")
    @ResponseBody
    public ResMsg mq_send() {
        for (int i = 1; i <= 50; i++) {
            User user = new User(new Long(i));
            user.setNickname("fei");
            String json = JSONUtils.toJSONString(user);
            System.out.println("A Sender : " + json);
            producer2.send("topic.msg.a", json);
        }

        /*System.out.println("########################");

        for (int i = 0; i < 5; i++) {
            String context = "B hello " + i;
            System.out.println("B Sender : " + context);
            producer2.send("topic.msg.b", context);
        }

        System.out.println("########################");

        for (int i = 0; i < 5; i++) {
            producer2.send("topic.msg.any", "any " + i);
        }*/
        return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg());
    }

}
