package com.jf.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2020-06-02
 * Time: 14:45
 */
@ControllerAdvice
public class BaseExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(BaseExceptionHandler.class);

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Object exceptionHandler(Throwable e, HttpServletRequest request) {
        TreeMap<String, String> rvs = new TreeMap<String, String>();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String name = (String) paramNames.nextElement();
            String value = request.getParameter(name);
            rvs.put(name, value);
        }
        String s = "Error: {0}, Msg: {1}, Caused by {2}.{3}:{4}, Uri: {5}, Params: {6}";
        s = MessageFormat.format(s,
                e.getClass().getName(),
                e.getMessage(),
                e.getStackTrace()[0].getClassName(),
                e.getStackTrace()[0].getMethodName(),
                e.getStackTrace()[0].getLineNumber(),
                request.getRequestURI(),
                rvs);
        log.error(s, e);

        Map<String, Object> msg = new HashMap<>();
        msg.put("code", 500);
        msg.put("msg", "error");
        return msg;
    }

}
