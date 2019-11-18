package com.jf.system.aspect2;

import com.jf.common.TokenHandler;
import com.jf.exception.AppException;
import com.jf.exception.AppTokenException;
import com.jf.string.StringUtil;
import com.jf.system.conf.IConstant;
import org.apache.ibatis.javassist.*;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: App Token
 * User: xujunfei
 * Date: 2018-05-24
 * Time: 15:07
 */
//@Aspect
//@Component
//@Order(2)
public class AspectAuthToken {

    private final static Logger log = LoggerFactory.getLogger(AspectAuthToken.class);
    private final static String[] whiteUriList = {"/app/login", "/app/logout"};

    @Autowired(required = false)
    private RedisTemplate redisTemplate;
    @Autowired
    private TokenHandler tokenHandler;

    // 所有控制器下的RestController
    @Pointcut("execution(public * com.jf.controller..*.*(..))&&@within(org.springframework.web.bind.annotation.RestController)")
    public void app() {
    }

    /**
     * 注意：
     * 1. 注解方法中，参数必须包含Principal
     * 2. 默认拦截所有请求，对于不拦截token请求，请加入URI白名单；对于不校验权限，请加入@NoAuth()注解
     *
     * @param pjp
     * @param tk
     */
    @Around("app()")
    public Object token(ProceedingJoinPoint pjp) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        try {
            String uri = request.getRequestURI();
            for (String s : whiteUriList) {
                if (s.equals(uri)) {
                    return pjp.proceed();
                }
            }

            Object[] args = pjp.getArgs();
            if (args.length < 1) {
                throw new AppTokenException("必须指定一个参数");
            }

            // 如果是接入了OAuth，必须在Header中传值，且格式为：Authorization: Bearer b634231b-7b8f-454d-a441-fa5c86c21764
            String token = request.getHeader(IConstant.TOKEN);
            Long uid = tokenHandler.getIdByTokenFromDb(token);
            Admin admin = new Admin(); // 可以是从数据库查询出来的用户数据
            admin.setId(uid);
            Principal<Admin> principal = new Principal().set(admin).accessKey(token);
            args[0] = principal;
            return pjp.proceed(args);
        } catch (Throwable throwable) {
            if (throwable instanceof AppTokenException) {
                throw new AppTokenException(throwable.getMessage(), throwable);
            } else if (throwable instanceof NullPointerException) {
                throw new AppException("NullPointerException", throwable);
            } else {
                throw new AppException(StringUtil.isBlank(throwable.getMessage()) ? "Null" : throwable.getMessage(), throwable);
            }
        }
    }

    //本例中固定第一个参数为 Principal，为方便赋值；可使用以下代码进行查找对应位置再进行赋值
    /*String classType = pjp.getTarget().getClass().getName();
            Class<?> clazz = Class.forName(classType);
            String clazzName = clazz.getName();
            String methodName = pjp.getSignature().getName();
            Map<String, Object> nameAndArgs = getFieldsName(this.getClass(), clazzName, methodName, args);
            System.out.println(nameAndArgs);*/

    private Map<String, Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args) throws NotFoundException {
        Map<String, Object> map = new HashMap<String, Object>();

        ClassPool pool = ClassPool.getDefault();
        //ClassClassPath classPath = new ClassClassPath(this.getClass());
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            // exception
        }
        // String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < cm.getParameterTypes().length; i++) {
            map.put(attr.variableName(i + pos), args[i]);
        }
        return map;
    }

}
