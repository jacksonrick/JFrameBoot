package com.jf.auth;

import com.jf.exception.PermissionDenyException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2020-06-02
 * Time: 14:05
 */
@Aspect
@Component
public class AspectAuth {

    private static final Logger log = LoggerFactory.getLogger(AspectAuth.class);

    @Pointcut("@annotation(com.jf.auth.AuthPassport)")
    public void app() {
    }

    @Around("app()")
    public void token(ProceedingJoinPoint pjp) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 权限校验
        String action = request.getRequestURI();
        log.info("Action: " + action);
        /*if (!authService.checkHavingRight(adminId, action)) {
            throw new NotAllowException(ResCode.NOT_ALLOW.msg());
        }*/

        /*if (1 == 1) {
            throw new PermissionDenyException("拒绝访问");
        }*/
    }

}
