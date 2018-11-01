package com.jf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-11-01
 * Time: 10:52
 */
@Controller
public class MainController {

    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return "Welcome to OAuth2.0";
    }

}
