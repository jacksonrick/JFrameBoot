package com.jf.system.interceptor;

import com.jf.system.PathUtil;
import com.jf.system.conf.LogManager;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * APP拦截
 *
 * @author rick
 */
public class AppInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LogManager.visit(request);

        // app原生使用接口[JSON数据]，需要在控制器注解 @RestController ,页面使用 @Controller
        RestController rest = ((HandlerMethod) handler).getMethod().getDeclaringClass().getAnnotation(RestController.class);
        if (rest != null) {
            // 验证header
            String appHeader = request.getHeader("Req-Type");
            if (appHeader == null || !"APP".equals(appHeader)) {
                PathUtil.output(97, "非法请求", response);
                return false;
            }
            // 验证APPKEY
        }

        return true;
    }

}
