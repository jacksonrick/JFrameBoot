package com.jf.system.interceptor;

import com.jf.entity.ResMsg;
import com.jf.json.JSONUtils;
import com.jf.model.User;
import com.jf.system.LogManager;
import com.jf.system.annotation.Login;
import com.jf.system.conf.SysConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/**
 * 商家拦截
 *
 * @author rick
 * @version 3.0
 */
@Component
public class ShopInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 请求类型
        String requestType = request.getHeader("X-Requested-With");
        // Action
        String path = request.getServletPath();
        // IP
        String remote = request.getHeader("x-forwarded-for") == null ? request.getRemoteAddr() : request.getHeader("x-forwarded-for");
        String visit = (remote + "==>" + path);

        try {
            User user = (User) request.getSession().getAttribute(SysConfig.SESSION_USER);
            if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
                Login login = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
                // 未指定【不检查登录】
                if (login == null) {
                    log("Action", request);
                    return true;
                }
                // 指定
                if (user == null) {
                    String rurl = request.getContextPath() + "/shop/login";
                    output(requestType, rurl, response, request);
                    log("Login", request);
                    return false;
                }
                log("Action", request);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 跳转页面
     */
    public void output(String requestType, String url, HttpServletResponse response, HttpServletRequest request) throws IOException {
        if ("XMLHttpRequest".equalsIgnoreCase(requestType)) { // AJAX
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter pw = response.getWriter();
            pw.print(JSONUtils.toJSONString(new ResMsg(99, "未登录", url)));
            pw.flush();
            pw.close();
        } else { //普通请求
            response.sendRedirect(url);
        }
    }

    /**
     * @param extra
     * @param request
     */
    public void log(String extra, HttpServletRequest request) {
        // Action
        String path = request.getRequestURI();
        // IP
        String remote = request.getHeader("x-forwarded-for") == null ? request.getRemoteAddr() : request.getHeader("x-forwarded-for");
        Map<String, String[]> parameters = request.getParameterMap();
        String param = "";
        if (!parameters.isEmpty()) {
            Set<String> keys = parameters.keySet();
            for (String key : keys) {
                String[] params = parameters.get(key);
                param += "|" + key + "=" + params[0];
            }
            param += "|";
        } else {
            param = "None";
        }
        LogManager.visit(remote, extra, path, param);
    }
}
