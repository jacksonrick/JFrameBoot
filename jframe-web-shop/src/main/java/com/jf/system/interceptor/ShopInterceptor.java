package com.jf.system.interceptor;

import com.jf.entity.ResMsg;
import com.jf.json.JSONUtils;
import com.jf.model.User;
import com.jf.system.PathUtil;
import com.jf.system.conf.LogManager;
import com.jf.system.annotation.Login;
import com.jf.system.conf.SysConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

/**
 * 商家拦截
 *
 * @author rick
 * @version 3.0
 */
public class ShopInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LogManager.visit(request);
        try {
            User user = (User) request.getSession().getAttribute(SysConfig.SESSION_USER);
            if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
                Login login = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
                // 未指定【不检查登录】
                if (login == null) {
                    return true;
                }
                // 指定
                if (user == null) {
                    PathUtil.nologin(request.getContextPath() + "/shop/login", response, request);
                    return false;
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

}
