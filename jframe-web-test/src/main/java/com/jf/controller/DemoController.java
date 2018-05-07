package com.jf.controller;

import com.github.pagehelper.PageInfo;
import com.jf.entity.ResMsg;
import com.jf.database.model.User;
import com.jf.database.model.custom.IdText;
import com.jf.service.user.UserService;
import com.jf.string.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: page demos
 * User: xujunfei
 * Date: 2018-03-14
 * Time: 11:39
 */
@Controller
public class DemoController extends BaseController {

    @Resource
    private UserService userService;

    @RequestMapping(path = {"/index", "/"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping("/demo/{path}")
    public String demo(@PathVariable("path") String path) {
        return "demo/" + path;
    }

    @RequestMapping("/login")
    @ResponseBody
    public ResMsg login(String account) {
        return new ResMsg(0, SUCCESS, account);
    }

    @RequestMapping("/users")
    @ResponseBody
    public PageInfo users(Integer pageNo, String pageSort, String keywords) {
        User condition = new User();
        condition.setKeywords(keywords);
        condition.setPageNo(pageNo);
        if (StringUtil.isBlank(pageSort)) {
            condition.setPageSort("id DESC");
        } else {
            condition.setPageSort(pageSort);
        }
        return userService.findUserByPage(condition);
    }

    @RequestMapping("/users2")
    @ResponseBody
    public ResMsg users2() {
        User condition = new User();
        List<User> list = userService.findByCondition(condition);
        return new ResMsg(0, SUCCESS, list);
    }

    @RequestMapping("/findUsers")
    @ResponseBody
    public List<IdText> findUsers(String phone) {
        return userService.findUserLikePhone(phone);
    }

    @RequestMapping("/user")
    @ResponseBody
    public ResMsg user() {

        return new ResMsg(0, SUCCESS);
    }

    @RequestMapping("/chartData")
    @ResponseBody
    public List<String[]> chartData() {
        List<String[]> list = new ArrayList<String[]>();
        String[] obj1 = new String[]{"2013/1/24", "2320.26", "2320.26", "2287.3", "2362.94"};
        String[] obj2 = new String[]{"2013/1/24", "2320.26", "2320.26", "2287.3", "2362.94"};
        String[] obj3 = new String[]{"2013/1/24", "2320.26", "2320.26", "2287.3", "2362.94"};

        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        return list;
    }

}
