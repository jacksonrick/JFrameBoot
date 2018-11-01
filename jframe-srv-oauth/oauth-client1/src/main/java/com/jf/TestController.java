package com.jf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    /**
     * 退出登录
     *
     * @param session
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:http://127.0.0.1:8090/logout";
    }

}
