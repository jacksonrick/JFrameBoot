package com.jf.controller;

import com.github.pagehelper.PageInfo;
import com.jf.entity.ResMsg;
import com.jf.model.User;
import com.jf.model.custom.IdText;
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

    @RequestMapping("/findUsers")
    @ResponseBody
    public List<IdText> findUsers(String phone) {
        return userService.findUserLikePhone(phone);
    }

    @RequestMapping("/chartData")
    @ResponseBody
    public List<Object> chartData() {
        List<Object> list = new ArrayList<Object>();
        Object[] obj = new Object[]{"2013/1/24", 2320.26, 2320.26, 2287.3, 2362.94};
        Object[] obj1 = new Object[]{"2013/1/25", 2300.2, 2291.3, 2288.26, 2308.38};
        Object[] obj2 = new Object[]{"2013/1/30", 2360.75, 2382.48, 2347.89, 2383.76};
        Object[] obj3 = new Object[]{"2013/2/1", 2377.41, 2419.02, 2369.57, 2421.15};
        Object[] obj4 = new Object[]{"2013/2/4", 2425.92, 2428.15, 2417.58, 2440.38};
        Object[] obj5 = new Object[]{"2013/2/5", 2411.3, 2433.13, 2403.3, 2437.42};
        Object[] obj6 = new Object[]{"2013/3/1", 2364.54, 2359.51, 2330.86, 2369.65};
        list.add(obj);
        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);
        list.add(obj5);
        list.add(obj6);
        return list;
    }

}
