package com.jf;

import com.jf.database.model.User;
import com.jf.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-06-19
 * Time: 09:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyTest {

    @Resource
    private UserService userService;

    @Test
    public void test01() {
        int result = userService.testInsertUserForJson();
        System.out.println(result);
    }

    @Test
    public void test02() {
        List<User> list = userService.testFindUserForJson();
        System.out.println(list);
    }

}
