package com.jf;

import com.jf.model.User;
import com.jf.system.conf.SysConfig;
import com.jf.system.mq.Producer;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.ObjectMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTests {

    @Test
    public void test1() {

    }

}
