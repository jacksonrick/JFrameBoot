package com.jf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-07-02
 * Time: 13:55
 */
@Controller
public class IndexController {

    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "manager";
    }

}
