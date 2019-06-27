package com.jf.system.aspect;

import com.jf.database.model.User;
import com.jf.database.enums.ResCode;
import com.jf.system.conf.IConstant;
import com.jf.system.exception.NoLoginException;
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
 * Description: Login
 * User: xujunfei
 * Date: 2018-05-24
 * Time: 15:07
 */
@Aspect
@Component
@Order(1)
public class AspectLogin {

    private final static Logger log = LoggerFactory.getLogger(AspectLogin.class);

    @Pointcut("@annotation(com.jf.annotation.Login)")
    public void login() {
    }

    /**
     * 请求之前拦截，并进行登录验证
     *
     * @param point
     */
    @Before("login()")
    public void login(JoinPoint point) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        User user = (User) request.getSession().getAttribute(IConstant.SESSION_USER);
        if (user == null) {
            throw new NoLoginException(ResCode.NO_LOGIN.msg());
        }
    }

}
