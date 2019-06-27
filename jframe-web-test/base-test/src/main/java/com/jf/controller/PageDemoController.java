package com.jf.controller;

import com.jf.common.BaseController;
import com.jf.service.UserService;
import com.jf.system.conf.SysConfig;
import com.jf.system.third.geet.GeetestLib;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: page demos
 * User: xujunfei
 * Date: 2018-03-14
 * Time: 11:39
 */
@Controller
public class PageDemoController extends BaseController {

    @Resource
    private UserService userService;

    @Resource
    private SysConfig config;

    /**
     * geetest验证
     *
     * @param request
     * @return
     */
    @RequestMapping("/startCaptcha")
    @ResponseBody
    public Map<String, Object> startCaptcha(HttpServletRequest request) {
        GeetestLib gtSdk = new GeetestLib(config.getGeetest().getId(), config.getGeetest().getKey(), true);
        int gtServerStatus = gtSdk.preProcess();
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        return gtSdk.getResponse();
    }

    @RequestMapping(path = {"/index", "/"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping("/demo/{path}")
    public String demo(@PathVariable("path") String path) {
        return "demo/" + path;
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
