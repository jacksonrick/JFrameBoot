package com.jf.system.conf;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 自定义错误页面
 *              也可以继承 DefaultErrorAttributes 重写 getErrorAttributes()
 * User: xujunfei
 * Date: 2018-05-25
 * Time: 13:29
 */
@Component
public class ErrorConfig implements ErrorViewResolver {

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        if (HttpStatus.NOT_FOUND.value() == status.value()) {
            return new ModelAndView("error/404");
        } else if (HttpStatus.BAD_REQUEST.value() == status.value()) {
            return new ModelAndView("error/400");
        } else if (HttpStatus.INTERNAL_SERVER_ERROR.value() == status.value()) {
            return new ModelAndView("error/500");
        } else if (HttpStatus.UNAUTHORIZED.value() == status.value() || HttpStatus.FORBIDDEN.value() == status.value()) {
            return new ModelAndView("error/refuse");
        } else {
            return new ModelAndView("error/unknow");
        }
    }

}
