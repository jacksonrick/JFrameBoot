package com.jf.controller;

import com.jf.entity.ResMsg;
import com.jf.model.User;
import com.jf.service.system.SystemService;
import com.jf.string.StringUtil;
import com.jf.system.Constant;
import com.jf.system.LogManager;
import com.jf.system.cache.RedisClientTemplate;
import com.jf.system.conf.SysConfig;
import com.jf.system.exception.UserException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

/**
 * controller基类
 *
 * @author rick
 * @version 1.0
 */
public class BaseController extends Constant {

    @Resource
    private SystemService systemService;
    @Resource
    private RedisClientTemplate template;

    protected ResMsg expLogin = new ResMsg(99, "登录已过期，请重新登录");

    /**
     * 获取当前用户
     *
     * @param request
     * @return
     */
    protected User getUser(HttpServletRequest request) {
        Object object = WebUtils.getSessionAttribute(request, SysConfig.SESSION_USER);
        if (object != null) {
            return (User) object;
        } else {
            return null;
        }
    }

    /**
     * 绑定token
     *
     * @param userId
     */
    public String putUser(Long userId) {
        String newToken = StringUtil.getTokenId();
        String oldToken = (String) template.getSet(PREFIX + userId, newToken);
        if (oldToken != null) {
            template.del(oldToken); // 删除旧token
        }
        template.set(newToken, userId + ""); // 绑定用户唯一token
        return newToken;
    }

    /**
     * 统一异常处理
     *
     * @param exception
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public Object exceptionHandler(Exception exception, HttpServletRequest request, HttpServletResponse response) {
        LogManager.info("An exception happened in [" + request.getRequestURI() + "], See the error log file.");
        Map<String, String[]> parameters = request.getParameterMap();
        String param = "";
        if (!parameters.isEmpty()) {
            Set<String> keys = parameters.keySet();
            for (String key : keys) {
                String[] params = parameters.get(key);
                param += "|" + key + "=" + params[0];
            }
            param += "|";
        } else {
            param = "None";
        }
        LogManager.error("App Request Error!!! Params:" + param, exception);
        return new ResMsg(-1, SERVER_ERROR);
    }

    /**
     * 自定义的异常处理
     *
     * @param exception
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(value = {UserException.class})
    @ResponseBody
    public Object userExceptionHandler(Exception exception, HttpServletRequest request, HttpServletResponse response) {
        return new ResMsg(99, exception.getMessage());
    }

}
