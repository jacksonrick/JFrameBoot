package com.jf.srvs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * Description: 服务发现 测试
 * User: xujunfei
 * Date: 2019-05-07
 * Time: 11:01
 */
@RestController
public class ZkController {

    @Autowired
    public ServiceRegistry serviceRegistry;

    @GetMapping("/getValues")
    public String getValues() {
        ArrayList<String> arrays = serviceRegistry.getValue("/registry/JTEST"); // registry-path / serviceName
        return arrays.toString();
    }

}

