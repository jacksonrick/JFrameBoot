package com.jf.controller;

import com.jf.entity.ResMsg;
import com.jf.model.Admin;
import com.jf.service.system.SystemService;
import com.jf.system.Constant;
import com.jf.system.LogManager;
import com.jf.system.conf.SysConfig;
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
public class BaseController extends Constant {

    @Resource
    private SystemService systemService;
    @Resource
    private SysConfig config;

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
     * 统一异常处理
     * <p>只会拦截Controller层发生的错误</p>
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
            Map map = new HashMap();
            map.put("msg", exception.toString());
            return new ModelAndView("error/500", map);
        }
    }

}
