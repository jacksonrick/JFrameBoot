package com.jf.system;

import com.jf.database.model.User;
import com.jf.system.annotation.Login;
import com.jf.system.conf.LogManager;
import com.jf.system.conf.SysConfig;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/**
 * 用户登录拦截
 *
 * @author rick
 */
public class MyInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LogManager.visit(request);

        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            Login login = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
            // 未指定【不检查登录】
            if (login == null) {
                return true;
            }
            // 指定
            User user = (User) request.getSession().getAttribute(SysConfig.SESSION_USER);
            if (user == null) {
                String url = request.getRequestURL().toString();
                Map<String, String[]> parameters = request.getParameterMap();
                String para_URL = "";
                if (parameters.isEmpty()) {
                    para_URL = URLEncoder.encode(url, "UTF-8");
                } else {
                    Set<String> keys = parameters.keySet();
                    String _para = "";
                    for (String key : keys) {
                        String[] params = parameters.get(key);
                        _para += "&" + key + "=" + params[0];
                    }
                    para_URL = URLEncoder.encode(url + "?" + _para.substring(1), "UTF-8");
                }
                // para_URL记录登录前的URL
                String rurl = request.getContextPath() + "/login.do?redirectURL=" + para_URL;

                PathUtil.nologin(rurl, response, request);
                return false;
            }
            return true;
        }
        return false;

    }

}
