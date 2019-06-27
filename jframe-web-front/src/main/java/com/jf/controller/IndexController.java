package com.jf.controller;

import com.jf.entity.ResMsg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @RequestMapping(path = {"/index", "/"})
    public String index() {
        return "index";
    }

    @RequestMapping("/test")
    @ResponseBody
    public ResMsg test() {
        return ResMsg.success();
    }

}
