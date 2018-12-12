package com.jf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-11-01
 * Time: 10:49
 */
@Controller
public class TestController {

    @RequestMapping("/test/a")
    @ResponseBody
    public String test() {
        System.out.println("test");
        return "test";
    }

}
