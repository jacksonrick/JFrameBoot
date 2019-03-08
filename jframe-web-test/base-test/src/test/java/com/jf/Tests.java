package com.jf;

import com.jf.database.mapper.ITestMapper;
import com.jf.database.mapper.TestMapper;
import com.jf.database.mapper.manage.AdminMapper;
import com.jf.database.model.User;
import com.jf.json.JacksonUtil;
import com.jf.service.TxService;
import com.jf.service.UserService;
import com.jf.system.conf.SysConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * JUnit单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Tests {

    @Resource
    private SysConfig config;

    @Resource
    private TxService txService;

    @Resource
    private UserService userService;

    @Test
    public void test1() {
        System.out.println(config.getServerId());
    }

    @Test
    public void test2() {
        txService.tx1();
    }

    @Test
    public void test3() {
        int result = userService.testTypeHandlerForInsert();
        System.out.println(result);
    }

    @Test
    public void test4() {
        User list = userService.testTypeHandlerForFind();
        System.out.println(JacksonUtil.objectToJson(list));
    }


    @Resource
    private ITestMapper iTestMapper;
    @Resource
    private AdminMapper adminMapper;

    @Test
    public void test5() {
        // 相同包名
        // 接口类不能相同
        System.out.println(iTestMapper.find());
        System.out.println(adminMapper.findById(10000));
    }

}
