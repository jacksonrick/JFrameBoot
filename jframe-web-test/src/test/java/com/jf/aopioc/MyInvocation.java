package com.jf.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * Description: 动态代理-JDK
 * User: xujunfei
 * Date: 2018-04-04
 * Time: 13:31
 */
public class MyInvocation implements InvocationHandler {

    public Object target;

    public MyInvocation(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().startsWith("test")) {
            System.out.println("代理中：" + method.getName());
        }
        return method.invoke(target, args);
    }

}
