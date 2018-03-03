package com.jf;

import com.jf.model.User;
import com.jf.system.conf.SysConfig;
import com.jf.system.conf.UserException;
import com.jf.system.jms.activemq.Producer;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.jms.Destination;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FrontTests {

    @Resource
    private SysConfig config;

    @Resource
    private RedisTemplate redisTemplate;

    @Autowired(required = false)
    private Producer producer;

    @Test
    public void test0() {
        System.out.println(config.getServerId());
    }

    @Test
    public void test2() {
        redisTemplate.opsForValue().set("name", "xujunfei");
        System.out.println((String) redisTemplate.opsForValue().get("name"));
        redisTemplate.delete("name");
        String res = (String) redisTemplate.opsForValue().getAndSet("name", "hahahahah");
        System.out.println(res);
    }

    @Test
    public void test3() {
        Destination destination = new ActiveMQQueue("test.queue");
        User user = new User(1000l);
        user.setNickname("hahha");
        producer.sendObjectMessage(destination, user);
    }

    @Test
    public void test4() {

    }

}
