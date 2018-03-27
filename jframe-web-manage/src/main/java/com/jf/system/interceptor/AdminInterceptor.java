package com.jf.system.interceptor;

import com.jf.model.Admin;
import com.jf.service.system.ModuleService;
import com.jf.system.PathUtil;
import com.jf.system.conf.LogManager;
import com.jf.system.annotation.AuthPassport;
import com.jf.system.conf.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 管理登录|权限 拦截
 *
 * @author rick
 * @version 4.0
 */
public class AdminInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ModuleService moduleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LogManager.visit(request);

        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            AuthPassport authPassport = ((HandlerMethod) handler).getMethodAnnotation(AuthPassport.class);
            // 未指定【不检查登录和操作权限】 或 指定【检查登录声明不验证】
            if (authPassport == null || !authPassport.login()) {
                return true;
            }

            Admin admin = (Admin) request.getSession().getAttribute(SysConfig.SESSION_ADMIN);
            // 检查登陆
            if (checkLogin(admin, request, response)) {
                // 检查操作权限
                if (!authPassport.right()) {
                    return true;
                }
                // 请求的Action
                String action = request.getRequestURI();
                return checkRight(action, admin, request, response);
            }
            return false;
        }
        return false;
    }

    /**
     * 检查登录
     */
    public boolean checkLogin(Admin admin, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (admin == null) {
            PathUtil.nologin("/admin/login", response, request);
            return false;
        }
        return true;
    }

    /**
     * 检查权限
     */
    public boolean checkRight(String action, Admin admin, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (moduleService.checkHavingRight(admin.getRole().getId(), action)) {
            return true;
        }
        PathUtil.refuse("/error/refuse", response, request);
        return false;

    }

}
