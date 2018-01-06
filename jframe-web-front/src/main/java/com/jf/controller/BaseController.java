package com.jf.controller;

import com.jf.entity.ResMsg;
import com.jf.model.User;
import com.jf.service.system.SystemService;
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
public class BaseController {

    @Resource
    private SystemService systemService;

    /********************************
     * 关键字：VIEW TYPE STATUS
     ********************************/

    protected String SUCCESS = "SUCCESS";
    protected String ERROR = "ERROR";
    protected String FAIL = "FAIL";
    protected String NODATA = "未查询到数据";
    protected String SERVER_ERROR = "服务器开小差了，请稍后再试！";

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
        LogManager.error("front error log", exception);
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
