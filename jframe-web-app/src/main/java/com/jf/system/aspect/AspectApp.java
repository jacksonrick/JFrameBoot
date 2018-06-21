package com.jf.system.aspect;

import com.jf.system.exception.AppException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Description: App
 * User: xujunfei
 * Date: 2018-05-24
 * Time: 15:07
 */
@Aspect
@Component
@Order(1)
public class AspectApp {

    private final static Logger log = LoggerFactory.getLogger(AspectApp.class);

    @Pointcut("execution(public * com.jf.controller..*.*(..))&&@within(org.springframework.web.bind.annotation.RestController)")
    public void app() {
    }

    /**
     * @param point
     */
    @Before("app()")
    public void app(JoinPoint point) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 验证header
        String appHeader = request.getHeader("Req-Type");
        if (!"APP".equals(appHeader)) {
            throw new AppException("非法请求");
        }
        // 验证APPKEY

    }

}
