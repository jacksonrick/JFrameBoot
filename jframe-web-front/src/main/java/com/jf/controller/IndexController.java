package com.jf.controller;

import com.jf.annotation.Login;
import com.jf.database.model.User;
import com.jf.entity.ResMsg;
import com.jf.entity.enums.ResCode;
import com.jf.system.conf.IConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

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
