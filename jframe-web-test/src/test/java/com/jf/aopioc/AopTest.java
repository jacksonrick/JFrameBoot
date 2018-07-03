package com.jf.aop;

import com.jf.MainTest;

import java.lang.reflect.Proxy;

/**
 * Created with IntelliJ IDEA.
 * Description: 测试动态代理
 * User: xujunfei
 * Date: 2018-04-12
 * Time: 11:26
 */
public class AopTest {

    public static void main(String[] args) {
        // 代理接口
        MyInvocation invocation = new MyInvocation(new UserServiceImpl());
        // 获取代理类
        UserService service = (UserService) Proxy.newProxyInstance(MainTest.class.getClassLoader(),
                new Class[]{UserService.class}, invocation);
        service.test1();
        service.test2();
        service.haha();
    }

}
