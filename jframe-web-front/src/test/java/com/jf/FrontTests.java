package com.jf;

import com.jf.system.conf.SysConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FrontTests {

    @Resource
    private SysConfig config;

    @Test
    public void test0() {
        System.out.println(config.getServerId());
    }

}
