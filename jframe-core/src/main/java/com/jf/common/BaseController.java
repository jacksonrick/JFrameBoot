package com.jf.common;

import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Base Controller
 *
 * @author rick
 * @version 2.0
 */
public class BaseController {

    /**
     * 从SESSION获取登录信息
     *
     * @param request
     * @param sessionName
     * @return
     */
    protected <T> T getSession(HttpServletRequest request, String sessionName) {
        Object object = WebUtils.getSessionAttribute(request, sessionName);
        if (object != null) {
            return (T) object;
        } else {
            return null;
        }
    }

}
