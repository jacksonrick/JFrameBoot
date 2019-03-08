package com.jf.system.aspect;

import com.jf.annotation.Token;
import com.jf.entity.enums.ResCode;
import com.jf.string.StringUtil;
import com.jf.system.conf.IConstant;
import com.jf.system.exception.AppException;
import com.jf.system.exception.AppTokenException;
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
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Description: App Token
 * User: xujunfei
 * Date: 2018-05-24
 * Time: 15:07
 */
@Aspect
@Component
@Order(2)
public class AspectToken {

    private final static Logger log = LoggerFactory.getLogger(AspectToken.class);

    @Autowired(required = false)
    private RedisTemplate redisTemplate;

    @Pointcut("@annotation(com.jf.annotation.Token)")
    public void token() {
    }

    /**
     * 将token转换为userId
     * 注意：注解方法中，第一个参数必须要是Long param_name
     *
     * @param pjp
     * @param tk
     */
    @Around("token()&&@annotation(tk)")
    public Object token(ProceedingJoinPoint pjp, Token tk) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Object[] args = pjp.getArgs();
        if (args.length < 1) {
            throw new AppTokenException("必须指定一个参数");
        }

        String name = tk.name(); // 键名
        String type = tk.type(); // 类型 1-header 2-cookie
        boolean need = tk.need(); // 是否必须 默认：true

        try {
            if (IConstant.TOKEN_HEADER.equals(type)) {
                String token = request.getHeader(name);
                if (!need) {
                    if (StringUtil.isBlank(token)) {
                        return pjp.proceed();
                    } else {
                        Integer uid = (Integer) redisTemplate.opsForValue().get(token);
                        if (uid != null) {
                            args[0] = dealToken(token);
                            return pjp.proceed(args);
                        } else {
                            return pjp.proceed();
                        }
                    }
                } else {
                    if (StringUtil.isBlank(token)) {
                        throw new AppTokenException(ResCode.TOKEN_EXP.msg());
                    } else {
                        log.info(IConstant.TOKEN_HEADER + " token:" + token);
                        args[0] = dealToken(token);
                        return pjp.proceed(args);
                    }
                }
            } else if (IConstant.TOKEN_COOKIE.equals(type)) {
                Cookie cookieValue = WebUtils.getCookie(request, name);
                if (!need) {
                    if (cookieValue == null) {
                        return pjp.proceed();
                    } else {
                        String token = cookieValue.getValue();
                        if (StringUtil.isBlank(token)) {
                            return pjp.proceed();
                        }
                        Integer uid = (Integer) redisTemplate.opsForValue().get(token);
                        if (uid != null) {
                            args[0] = dealToken(token);
                            return pjp.proceed(args);
                        } else {
                            return pjp.proceed();
                        }
                    }
                } else {
                    if (cookieValue == null) {
                        throw new AppTokenException(ResCode.TOKEN_EXP.msg());
                    }
                    String token = cookieValue.getValue();
                    if (StringUtil.isNotBlank(token)) {
                        log.info(IConstant.TOKEN_COOKIE + " token:" + token);
                        args[0] = dealToken(token);
                        return pjp.proceed(args);
                    } else {
                        throw new AppTokenException(ResCode.TOKEN_EXP.msg());
                    }
                }
            } else {
                throw new AppException("APP接口异常: Invalid token type.");
            }
        } catch (Throwable throwable) {
            if (throwable instanceof AppTokenException) {
                throw new AppTokenException(throwable.getMessage());
            } else if (throwable instanceof NullPointerException) {
                throwable.printStackTrace();
                throw new AppException("NullPointerException");
            } else {
                throw new AppException(StringUtil.isBlank(throwable.getMessage()) ? "Null" : throwable.getMessage());
            }
        }
    }

    private Integer dealToken(String token) {
        if (token == null || token.length() < 1) {
            throw new AppTokenException(ResCode.TOKEN_EXP.msg());
        }
        Integer uid = (Integer) redisTemplate.opsForValue().get(token);
        if (uid != null) {
            return uid;
        } else {
            throw new AppTokenException(ResCode.TOKEN_EXP.msg());
        }
    }

}
