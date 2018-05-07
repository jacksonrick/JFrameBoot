package com.jf.controller;

import com.jf.entity.ResMsg;
import com.jf.database.model.Admin;
import com.jf.database.model.User;
import com.jf.service.system.SystemService;
import com.jf.string.StringUtil;
import com.jf.system.conf.Constant;
import com.jf.system.conf.LogManager;
import com.jf.system.conf.SysConfig;
import com.jf.system.exception.AppLoginException;
import com.jf.system.exception.SysException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * controller基类
 *
 * @author rick
 * @version 1.0
 */
@ControllerAdvice(basePackages = "com.jf.controller")
public class BaseController extends Constant {

    @Resource
    private SysConfig config;

    @Resource
    private SystemService systemService;

    @Autowired(required = false)
    private RedisTemplate redisTemplate;

    /**
     * 绑定token
     *
     * @param userId
     */
    public String bindToken(Long userId) {
        String newToken = StringUtil.getTokenId();
        String oldToken = (String) redisTemplate.opsForValue().getAndSet(PREFIX + userId, newToken);
        if (oldToken != null) {
            redisTemplate.delete(oldToken); // 删除旧token
        }
        redisTemplate.opsForValue().set(newToken, userId + ""); // 绑定用户唯一token
        return newToken;
    }

    /**
     * 管理操作日志
     *
     * @param request
     * @param remark
     * @param params
     */
    protected void addAdminLog(HttpServletRequest request, String remark, String params) {
        systemService.addLog(getAdmin(request).getAdminName(), remark, request.getRemoteAddr(), params);
    }

    /**
     * 从SESSION获取当前管理员信息
     *
     * @param request
     * @return
     */
    protected Admin getAdmin(HttpServletRequest request) {
        Object object = WebUtils.getSessionAttribute(request, SysConfig.SESSION_ADMIN);
        if (object != null) {
            return (Admin) object;
        } else {
            return null;
        }
    }

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
     * 统一异常处理
     *
     * @param exception
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public Object exceptionHandler(Exception exception, HttpServletRequest request, HttpServletResponse response) {
        // 跟踪错误信息
        LogManager.info("An exception happened in【Server ID：" + config.getServerId() + "】【URI：" + request.getRequestURI() + "】" + "【Msg：" + exception.getMessage() + "】");
        LogManager.error("【Server ID：" + config.getServerId() + "】【URI：" + request.getRequestURI() + "】", exception);

        String requestType = request.getHeader("X-Requested-With");
        String appHeader = request.getHeader("Req-Type");
        if ("APP".equals(appHeader)) {
            return new ResMsg(-1, SERVER_ERROR);
        }
        if ("XMLHttpRequest".equalsIgnoreCase(requestType)) {
            return new ResMsg(-1, SERVER_ERROR);
        } else {
            Map map = new HashMap();
            map.put("msg", exception.toString());
            return new ModelAndView("error/500", map);
        }
    }

    /**
     * 自定义的异常处理
     *
     * @param exception
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(value = {SysException.class})
    @ResponseBody
    public Object sysExceptionHandler(Exception exception, HttpServletRequest request, HttpServletResponse response) {
        return new ResMsg(99, exception.getMessage());
    }

    /**
     * app 登录异常
     *
     * @param exception
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(value = {AppLoginException.class})
    @ResponseBody
    public Object appExceptionHandler(Exception exception, HttpServletRequest request, HttpServletResponse response) {
        return new ResMsg(99, exception.getMessage());
    }

}
