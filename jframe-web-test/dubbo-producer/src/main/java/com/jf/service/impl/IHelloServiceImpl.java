package com.jf.service.impl;

import com.jf.service.IHelloService;
import org.apache.dubbo.config.annotation.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-03-19
 * Time: 12:04
 */
@Service(version = "1.0")
public class IHelloServiceImpl implements IHelloService {

    @Override
    public String hello(String name) {
        /*try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return "Hello " + name + "!";
    }

    @Override
    public String hello2() {
        return "HAHA";
    }

}
