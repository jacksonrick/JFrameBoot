package com.jf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2020-06-11
 * Time: 12:07
 */
@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login(String error, String logout, ModelMap map) {
        if (error != null) {
            map.addAttribute("msg", "用户名和密码错误");
        }
        if (logout != null) {
            map.addAttribute("msg", "你已经成功退出");
        }
        return "login";
    }

    @GetMapping("/bye")
    public String bye() {
        return "bye";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
