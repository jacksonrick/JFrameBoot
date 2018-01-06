package com.jf.controller;

import com.jf.entity.ResMsg;
import com.jf.model.User;
import com.jf.service.system.SystemService;
import com.jf.string.StringUtil;
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
public class BaseController {

    @Resource
    private SystemService systemService;
    @Resource
    private RedisClientTemplate template;

    /********************************
     * 关键字：VIEW TYPE STATUS
     ********************************/

    protected String SUCCESS = "SUCCESS";
    protected String ERROR = "ERROR";
    protected String FAIL = "FAIL";
    protected String NODATA = "未查询到数据";
    protected String INSERT_SUCCESS = "添加成功";
    protected String INSERT_FAIL = "添加失败";
    protected String UPDATE_SUCCESS = "更新成功";
    protected String UPDATE_FAIL = "更新失败";
    protected String DELETE_SUCCESS = "删除成功";
    protected String DELETE_FAIL = "删除失败";
    protected String OPERATE_SUCCESS = "操作成功";
    protected String OPERATE_FAIL = "操作失败";
    protected String INVALID_ID = "无效ID";
    protected String INVALID_STATE = "无效状态";
    protected String SERVER_ERROR = "服务器开小差了，请稍后再试！";

    protected ResMsg expLogin = new ResMsg(99, "登录已过期，请重新登录");
    protected String prefix = "UID";

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
        String oldToken = (String) template.getSet(prefix + userId, newToken);
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
