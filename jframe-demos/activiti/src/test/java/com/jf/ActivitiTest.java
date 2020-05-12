package com.jf;

import com.jf.service.ActivitiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-07-30
 * Time: 14:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiTest {

    @Resource
    private ActivitiService activitiService;

    @Test
    public void test1() {
        activitiService.addUser();
    }

    @Test
    public void test2() {
        activitiService.addGroup();
    }

    @Test
    public void test3() {
        System.out.println(activitiService.apply("1", 3));
    }

    @Test
    public void test4() {
        activitiService.applyList("2", 1, 10);
    }

}
