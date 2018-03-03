package com.jf.system.interceptor;

import com.jf.entity.ResMsg;
import com.jf.json.JSONUtils;
import com.jf.model.Admin;
import com.jf.service.system.ModuleService;
import com.jf.system.conf.LogManager;
import com.jf.system.annotation.AuthPassport;
import com.jf.system.conf.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 管理员登录|权限拦截
 *
 * @author rick
 * @version 3.0
 */
@Component
public class AdminInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ModuleService moduleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 请求类型
        String requestType = request.getHeader("X-Requested-With");
        // 请求的Action
        String action = request.getRequestURI();
        // IP
        //String remote = request.getHeader("x-forwarded-for") == null ? request.getRemoteAddr() : request.getHeader("x-forwarded-for");
        //String visit = (remote + "==>" + action);
        // Admin
        Admin admin = (Admin) request.getSession().getAttribute(SysConfig.SESSION_ADMIN);

        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            AuthPassport authPassport = ((HandlerMethod) handler).getMethodAnnotation(AuthPassport.class);
            // 未指定【不检查登录和操作权限】
            if (authPassport == null) {
                log("Action", request);
                return true;
            }
            // 指定【检查登录或操作权限】
            // 声明不验证
            if (authPassport.login() == false) {
                log("Action", request);
                return true;
            }
            // 检查登陆
            if (checkLogin(requestType, admin, request, response)) {
                // 检查操作权限
                if (authPassport.right() == false) {
                    log("Login", request);
                    return true;
                }
                log("Permission", request);
                return checkRight(action, requestType, admin, request, response);
            }
            return false;
        }
        return false;
    }

    /**
     * 检查登录
     */
    public boolean checkLogin(String requestType, Admin admin, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (admin == null) {
            output(new ResMsg(99, "未登录"), requestType, "/admin/login", response, request);
            return false;
        }
        return true;
    }

    /**
     * 检查权限
     */
    public boolean checkRight(String action, String requestType, Admin admin, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (moduleService.checkHavingRight(admin.getRole().getId(), action)) {
            return true;
        }
        output(new ResMsg(100, "拒绝访问"), requestType, "/error/refuse", response, request);
        return false;

    }

    /**
     * 跳转页面
     * <p>AJAX访问【99为未登录，100为无权访问】</p>
     */
    public void output(ResMsg resMsg, String requestType, String url, HttpServletResponse response, HttpServletRequest request) throws IOException {
        if ("XMLHttpRequest".equalsIgnoreCase(requestType)) { // AJAX请求
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter pw = response.getWriter();
            pw.print(JSONUtils.toJSONString(resMsg));
            pw.flush();
            pw.close();
        } else { //普通请求
            response.sendRedirect(request.getContextPath() + url);
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
