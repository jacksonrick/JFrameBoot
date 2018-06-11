package com.jf.common;

import com.jf.entity.ResMsg;
import com.jf.entity.enums.ResCode;
import com.jf.system.annotation.UnStack;
import com.jf.system.aspect.AspectLog;
import com.jf.system.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description: 统一异常处理(返回JSON或跳转页面)
 * User: xujunfei
 * Date: 2018-05-23
 * Time: 16:33
 */
@ControllerAdvice
public class BaseExceptionHandler {

    private final static Logger log = LoggerFactory.getLogger(BaseExceptionHandler.class);

    /**
     * 异常处理
     *
     * @param e
     * @param request
     * @return page or json
     * @see AspectLog
     * @see SysException
     * @see AppException
     * @see AppTokenException
     * @see NoLoginException
     * @see NotAllowException
     * @see AdminNoLoginException
     */
    @ExceptionHandler
    @ResponseBody
    public Object exceptionHandler(Throwable e, HttpServletRequest request) {
        // 如果异常类有`@Unstack`注解，则不输出Stacktrace
        Annotation[] annotations = e.getClass().getDeclaredAnnotations();
        if (annotations.length > 0 && annotations[0] instanceof UnStack) {
            log.error("Error: {}, Caused by {}.{}:{}",
                    e.getMessage(),
                    e.getStackTrace()[0].getClassName(),
                    e.getStackTrace()[0].getMethodName(),
                    e.getStackTrace()[0].getLineNumber());
        } else {
            log.error("Error: {}, Caused by {}.{}:{}",
                    e.getMessage(),
                    e.getStackTrace()[0].getClassName(),
                    e.getStackTrace()[0].getMethodName(),
                    e.getStackTrace()[0].getLineNumber(), e);
        }

        // 自定义的系统异常
        if (e instanceof SysException) {
            return new ResMsg(ResCode.ERROR.code(), e.getMessage());
        }
        // app异常
        if (e instanceof AppException) {
            return new ResMsg(ResCode.APP_ERROR.code(), e.getMessage());
        }
        // app token异常
        if (e instanceof AppTokenException) {
            return new ResMsg(ResCode.TOKEN_EXP.code(), e.getMessage());
        }

        String requestType = request.getHeader("X-Requested-With");
        // 未登录
        if (e instanceof NoLoginException) {
            if ("XMLHttpRequest".equalsIgnoreCase(requestType)) { // Ajax
                return new ResMsg(ResCode.NO_LOGIN.code(), ResCode.NO_LOGIN.msg());
            } else { // Page
                String url = request.getRequestURL().toString();
                Map<String, String[]> parameters = request.getParameterMap();
                String para_URL = "";
                try {
                    if (parameters.isEmpty()) {
                        para_URL = URLEncoder.encode(url, "UTF-8");
                    } else {
                        Set<String> keys = parameters.keySet();
                        String _para = "";
                        for (String key : keys) {
                            String[] params = parameters.get(key);
                            _para += "&" + key + "=" + params[0];
                        }
                        para_URL = URLEncoder.encode(url + "?" + _para.substring(1), "UTF-8");
                    }
                } catch (UnsupportedEncodingException ex) {
                    para_URL = "";
                }
                Map map = new HashMap();
                map.put("redirectURL", para_URL);
                return new ModelAndView("/loginPage", map);
            }
        }

        // 拒绝访问
        if (e instanceof NotAllowException) {
            if ("XMLHttpRequest".equalsIgnoreCase(requestType)) { // Ajax
                return new ResMsg(ResCode.NOT_ALLOW.code(), ResCode.NOT_ALLOW.msg());
            } else { // Page
                Map map = new HashMap();
                map.put("msg", e.getMessage());
                return new ModelAndView("error/refuse", map);
            }
        }
        // admin未登录
        if (e instanceof AdminNoLoginException) {
            if ("XMLHttpRequest".equalsIgnoreCase(requestType)) { // Ajax
                return new ResMsg(ResCode.NO_LOGIN.code(), ResCode.NO_LOGIN.msg());
            } else { // Page
                Map map = new HashMap();
                map.put("msg", e.getMessage());
                return new ModelAndView("/admin/login", map);
            }
        }

        // 以下为默认异常处理
        // APP
        String appHeader = request.getHeader("Req-Type");
        if ("APP".equals(appHeader)) {
            return new ResMsg(ResCode.ERROR.code(), ResCode.ERROR.msg());
        }
        if ("XMLHttpRequest".equalsIgnoreCase(requestType)) { // Ajax
            return new ResMsg(ResCode.ERROR.code(), ResCode.ERROR.msg());
        } else { // Page
            Map map = new HashMap();
            map.put("msg", e.getMessage());
            return new ModelAndView("error/500", map);
        }
    }

}
