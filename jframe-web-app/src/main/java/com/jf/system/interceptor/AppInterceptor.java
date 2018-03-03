package com.jf.system.interceptor;

import com.jf.entity.ResMsg;
import com.jf.json.JSONUtils;
import com.jf.system.conf.LogManager;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

/**
 * APP拦截
 *
 * @author rick
 */
public class AppInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log(request);
        // 验证header
        String appHeader = request.getHeader("Req-Type");
        if (appHeader == null || !"APP".equals(appHeader)) {
            output(97, "非法请求", response);
            return false;
        }
        // 验证APPKEY
        return true;
    }

    /**
     * @param code
     * @param msg
     * @param response
     * @throws IOException
     */
    public void output(Integer code, String msg, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter pw = response.getWriter();
        pw.print(JSONUtils.toJSONString(new ResMsg(code, msg)));
        pw.flush();
        pw.close();
    }

    /**
     * @param request
     */
    public void log(HttpServletRequest request) {
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
        LogManager.visit(remote, "Action", path, param);
    }

}
