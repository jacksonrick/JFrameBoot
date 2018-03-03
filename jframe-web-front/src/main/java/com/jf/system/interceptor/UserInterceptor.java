package com.jf.system.interceptor;

import com.jf.entity.ResMsg;
import com.jf.json.JSONUtils;
import com.jf.model.User;
import com.jf.system.conf.LogManager;
import com.jf.system.annotation.Login;
import com.jf.system.conf.SysConfig;
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
 * 用户登录拦截
 *
 * @author rick
 */
public class UserInterceptor extends HandlerInterceptorAdapter {

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
                    LogManager.info("【Front】Action:" + visit);
                    return true;
                }
                // 指定
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

                    output(requestType, rurl, response, request);
                    LogManager.info("【Front】Check Login,Action:" + visit);
                    return false;
                }
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
}
