package com.jf.system.interceptor;

import com.jf.entity.ResMsg;
import com.jf.json.JSONUtils;
import com.jf.system.PathUtil;
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
        LogManager.visit(request);
        // 验证header
        String appHeader = request.getHeader("Req-Type");
        if (appHeader == null || !"APP".equals(appHeader)) {
            PathUtil.output(97, "非法请求", response);
            return false;
        }
        // 验证APPKEY
        return true;
    }

}
