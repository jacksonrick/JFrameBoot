package com.jf;

import com.jf.system.PathUtil;
import com.jf.system.conf.SysConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JframeWebAppApplicationTests {

    @Test
    public void test1() {
        System.out.println(SysConfig.appkey);
        System.out.println(SysConfig.static_host);
        System.out.println(SysConfig.static_path);
        System.out.println(SysConfig.log_path);
        System.out.println("##################");
        System.out.println(PathUtil.static_path);
        System.out.println(PathUtil.log_path);
    }

    @Value("${spring.resources.static-locations}")
    public String location;

    @Test
    public void test2() {
        System.out.println(location);
    }
}
