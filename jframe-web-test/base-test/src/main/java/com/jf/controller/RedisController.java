package com.jf.controller;

import com.jf.common.BaseController;
import com.jf.database.model.User;
import com.jf.entity.ResMsg;
import com.jf.database.enums.ResCode;
import com.jf.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-06-21
 * Time: 13:58
 */
@Controller
public class RedisController extends BaseController {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private UserService userService;

    /**
     * 测试Spring缓存
     *
     * @return
     */
    @RequestMapping("/testCache1")
    @ResponseBody
    public ResMsg testCache1() {
        User user = userService.findUserById(10000);
        return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg(), user);
    }

    @RequestMapping("/testCache2")
    @ResponseBody
    public ResMsg testCache2() {
        User user = new User(10000l);
        user.setMoney(1000d);
        userService.updateUser(user);
        return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg());
    }

    /**
     * spring redis template
     *
     * @return
     */
    @RequestMapping("/redisTemplate")
    @ResponseBody
    public ResMsg redisTemplate() {
        redisTemplate.opsForValue().set("name", "xujunfei");
        System.out.println((String) redisTemplate.opsForValue().get("name"));
        //redisTemplate.delete("name");
        //String res = (String) redisTemplate.opsForValue().getAndSet("name", "hahahahah");
        return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg());
    }

    /**
     * 分布式锁
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/testLock")
    @ResponseBody
    public ResMsg testLock() throws Exception {
        userService.testLock(10000);
        return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg());
    }

    /**
     * session redis
     *
     * @param session
     * @return
     */
    @RequestMapping("/testSetSession")
    @ResponseBody
    public ResMsg testSetSession(HttpSession session) {
        session.setAttribute("name", "xujunfei");
        return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg());
    }

    @RequestMapping("/testGetSession")
    @ResponseBody
    public ResMsg testGetSession(HttpSession session) {
        return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg(), session.getAttribute("name"));
    }
}
