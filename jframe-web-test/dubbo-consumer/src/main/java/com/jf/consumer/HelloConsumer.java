package com.jf.consumer;

import com.jf.service.IHelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-03-19
 * Time: 12:05
 */
@RestController
public class HelloConsumer {

    @Reference(version = "1.0")
    private IHelloService iHelloService;

    @GetMapping("/hello")
    public String hello() {
        return iHelloService.hello("xu");
    }

    @GetMapping("/hello2")
    public String hello2() {
        return iHelloService.hello2();
    }

}
