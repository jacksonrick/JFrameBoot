package com.jf.config.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created with IntelliJ IDEA.
 * Description: 登录失败处理类
 * User: xujunfei
 * Date: 2018-11-13
 * Time: 10:30
 */
@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.sendRedirect("/authentication/require?error=" + URLEncoder.encode(exception.getMessage(), "UTF-8"));
    }
}
