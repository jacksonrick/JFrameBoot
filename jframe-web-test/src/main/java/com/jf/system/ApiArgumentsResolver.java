package com.jf.system;

import com.jf.string.StringUtil;
import com.jf.system.annotation.Token;
import com.jf.system.conf.SysConfig;
import com.jf.system.exception.AppLoginException;
import com.jf.system.exception.SysException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

/**
 * Created by xujunfei on 2017/8/1.
 */
public class ApiArgumentsResolver implements HandlerMethodArgumentResolver {

    @Autowired(required = false)
    private RedisTemplate redisTemplate;

    private String expMsg = "登录已过期，请重新登录";

    /**
     * 解析器是否支持当前参数
     */
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Token.class);
    }

    /**
     * 将request中的请求参数解析到当前Controller参数上
     *
     * @param parameter     需要被解析的Controller参数，此参数必须首先传给{@link #supportsParameter}并返回true
     * @param mavContainer  当前request的ModelAndViewContainer
     * @param webRequest    当前request
     * @param binderFactory 生成WebDataBinder实例的工厂
     * @return 解析后的Controller参数
     */
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //String parameterName = parameter.getParameterName();
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        Annotation[] methodAnnotations = parameter.getParameterAnnotations();
        for (Annotation annotation : methodAnnotations) {
            if (annotation instanceof Token) {
                Token ant = (Token) annotation;
                String name = ant.name(); // 键名
                String type = ant.type(); // 类型 1-header 2-cookie
                boolean need = ant.need(); // 是否必须 默认：true
                if (SysConfig.TOKEN_HEADER.equals(type)) {
                    String token = servletRequest.getHeader(name);
                    if (!need) {
                        if (StringUtil.isBlank(token)) {
                            return null;
                        } else {
                            String uid = (String) redisTemplate.opsForValue().get(token);
                            if (uid != null) {
                                return Long.parseLong(uid);
                            } else {
                                return null;
                            }
                        }
                    } else {
                        if (StringUtil.isBlank(token)) {
                            throw new AppLoginException(expMsg);
                        } else {
                            System.out.println(SysConfig.TOKEN_HEADER + " token:" + token);
                            return dealToken(token);
                        }
                    }
                } else if (SysConfig.TOKEN_COOKIE.equals(type)) {
                    Cookie cookieValue = WebUtils.getCookie(servletRequest, name);
                    if (!need) {
                        if (cookieValue == null) {
                            return null;
                        } else {
                            String token = cookieValue.getValue();
                            if (StringUtil.isBlank(token)) {
                                return null;
                            }
                            String uid = (String) redisTemplate.opsForValue().get(token);
                            if (uid != null) {
                                return Long.parseLong(uid);
                            } else {
                                return null;
                            }
                        }
                    } else {
                        if (cookieValue == null) {
                            throw new AppLoginException(expMsg);
                        }
                        String token = cookieValue.getValue();
                        if (StringUtil.isNotBlank(token)) {
                            System.out.println(SysConfig.TOKEN_COOKIE + " token:" + token);
                            return dealToken(token);
                        } else {
                            throw new AppLoginException(expMsg);
                        }
                    }
                } else {
                    throw new SysException("APP接口异常");
                }
            }
        }
        return null;
    }

    public Long dealToken(String token) {
        if (token == null || token.length() < 1) {
            throw new AppLoginException(expMsg);
        }
        String uid = (String) redisTemplate.opsForValue().get(token);
        if (uid != null) {
            return Long.parseLong(uid);
        } else {
            throw new AppLoginException(expMsg);
        }
    }

}
