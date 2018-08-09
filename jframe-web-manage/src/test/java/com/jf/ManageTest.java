package com.jf;

import com.jf.service.system.ConfigService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-06-19
 * Time: 09:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ManageTest {


    @Resource
    private ConfigService configService;

    @Test
    public void test01() {
        System.out.println(configService.getString("sys_version"));
    }

}
