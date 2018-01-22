package com.jf.controller;

import com.jf.entity.ResMsg;
import com.jf.model.User;
import com.jf.service.system.SystemService;
import com.jf.system.Constant;
import com.jf.system.LogManager;
import com.jf.system.conf.SysConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
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
    private SysConfig config;

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
        LogManager.error("An exception happened in【Server ID：" + config.getServerId() + "】【URI：" + request.getRequestURI() + "】", exception);
        String requestType = request.getHeader("X-Requested-With");
        String appHeader = request.getHeader("Req-Type");
        if (appHeader != null && "APP".equals(appHeader)) {
            return new ResMsg(-1, SERVER_ERROR);
        }
        if (requestType != null && "XMLHttpRequest".equalsIgnoreCase(requestType)) {
            return new ResMsg(-1, SERVER_ERROR);
        } else {
            return null;
        }
    }

}
