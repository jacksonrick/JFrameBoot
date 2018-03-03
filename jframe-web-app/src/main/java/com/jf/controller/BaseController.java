package com.jf.controller;

import com.jf.entity.ResMsg;
import com.jf.model.User;
import com.jf.service.system.SystemService;
import com.jf.string.StringUtil;
import com.jf.system.Constant;
import com.jf.system.conf.LogManager;
import com.jf.system.conf.SysConfig;
import com.jf.system.conf.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * controller基类
 *
 * @author rick
 * @version 1.0
 */
public class BaseController extends Constant {

    @Resource
    private SystemService systemService;
    @Resource
    private SysConfig config;

    @Autowired(required = false)
    private RedisTemplate redisTemplate;

    protected ResMsg expLogin = new ResMsg(99, "登录已过期，请重新登录");

    /**
     * 获取当前用户
     *
     * @param request
     * @return
     */
    protected User getUser(HttpServletRequest request) {
        Object object = WebUtils.getSessionAttribute(request, SysConfig.SESSION_USER);
        if (object != null) {
            return (User) object;
        } else {
            return null;
        }
    }

    /**
     * 绑定token
     *
     * @param userId
     */
    public String putUser(Long userId) {
        String newToken = StringUtil.getTokenId();
        String oldToken = (String) redisTemplate.opsForValue().getAndSet(PREFIX + userId, newToken);
        if (oldToken != null) {
            redisTemplate.delete(oldToken); // 删除旧token
        }
        redisTemplate.opsForValue().set(newToken, userId + ""); // 绑定用户唯一token
        return newToken;
    }

    /**
     * 统一异常处理
     * <p>APP请求均为JSON数据，无需判断请求类型</p>
     *
     * @param exception
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public Object exceptionHandler(Exception exception, HttpServletRequest request, HttpServletResponse response) {
        LogManager.error("An exception happened in【Server ID：" + config.getServerId() + "】【URI：" + request.getRequestURI() + "】", exception);
        return new ResMsg(-1, SERVER_ERROR);
    }

    /**
     * 自定义的异常处理
     *
     * @param exception
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(value = {UserException.class})
    @ResponseBody
    public Object userExceptionHandler(Exception exception, HttpServletRequest request, HttpServletResponse response) {
        return new ResMsg(99, exception.getMessage());
    }

}
