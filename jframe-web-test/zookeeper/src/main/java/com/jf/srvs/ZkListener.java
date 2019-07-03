package com.jf.srvs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created with IntelliJ IDEA.
 * Description: 服务注册
 * User: xujunfei
 * Date: 2019-05-07
 * Time: 10:59
 */
@Component
public class ZkListener implements ServletContextListener {

    @Value("${server.port}")
    private Integer port;

    @Autowired
    public ServiceRegistry serviceRegistry;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 注册本机地址信息等
        serviceRegistry.register("JTEST", String.format("%s:%d", "127.0.0.1", port));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
