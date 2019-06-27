package com.jf.system.aspect;

import com.jf.annotation.AuthPassport;
import com.jf.database.enums.ResCode;
import com.jf.database.model.manage.Admin;
import com.jf.service.system.ModuleService;
import com.jf.system.conf.IConstant;
import com.jf.system.exception.AdminNoLoginException;
import com.jf.system.exception.NotAllowException;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Description: AuthPassport
 * User: xujunfei
 * Date: 2018-05-24
 * Time: 15:07
 */
@Aspect
@Component
@Order(1)
public class AspectAuth {

    private final static Logger log = LoggerFactory.getLogger(AspectAuth.class);

    @Resource
    private ModuleService moduleService;

    @Pointcut("@annotation(com.jf.annotation.AuthPassport)")
    public void auth() {
    }

    /**
     * 请求之前拦截，并进行登录和权限验证
     *
     * @param point
     * @param authPassport
     */
    @Before("auth()&&@annotation(authPassport)")
    public void auth(JoinPoint point, AuthPassport authPassport) {
        // 未指定【不检查登录和操作权限】 或 指定【检查登录声明不验证】
        if (authPassport == null || !authPassport.login()) {
            return;
        }

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Admin admin = (Admin) request.getSession().getAttribute(IConstant.SESSION_ADMIN);
        if (admin == null) {
            throw new AdminNoLoginException(ResCode.NO_LOGIN.msg());
        }
        // 检查操作权限
        if (!authPassport.right()) {
            return;
        }
        // 内置管理组开放全部权限
        if (admin.getRole() != null && admin.getRole().getRoleFlag() == 0) {
            return;
        }
        // 请求的URI
        String action = request.getRequestURI();
        if (!moduleService.checkHavingRight(admin.getId(), action)) {
            throw new NotAllowException(ResCode.NOT_ALLOW.msg());
        }
    }

}
