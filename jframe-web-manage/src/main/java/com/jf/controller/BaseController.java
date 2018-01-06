package com.jf.controller;

import com.jf.entity.ResMsg;
import com.jf.model.Admin;
import com.jf.service.system.SystemService;
import com.jf.system.LogManager;
import com.jf.system.conf.SysConfig;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * controller基类
 *
 * @author rick
 * @version 1.0
 */
public class BaseController {

    @Resource
    private SystemService systemService;

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

    /**
     * 管理操作日志
     *
     * @param request
     * @param remark
     * @param params
     */
    protected void addAdminLog(HttpServletRequest request, String remark, String params) {
        systemService.addLog(getAdmin(request).getAdminName(), remark, request.getRemoteAddr(), params);
    }

    /**
     * 从SESSION获取当前管理员信息
     *
     * @param request
     * @return
     */
    protected Admin getAdmin(HttpServletRequest request) {
        Object object = WebUtils.getSessionAttribute(request, SysConfig.SESSION_ADMIN);
        if (object != null) {
            return (Admin) object;
        } else {
            return null;
        }
    }

    /**
     * 统一异常处理
     * <p>只会拦截Controller层发生的错误</p>
     *
     * @param exception
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public Object exceptionHandler(Exception exception, HttpServletRequest request, HttpServletResponse response) {
        // 跟踪错误信息
        LogManager.error("manage error log", exception);
        String requestType = request.getHeader("X-Requested-With");
        String appHeader = request.getHeader("Req-Type");
        if (appHeader != null && "APP".equals(appHeader)) {
            return new ResMsg(-1, SERVER_ERROR);
        }
        if (requestType != null && "XMLHttpRequest".equalsIgnoreCase(requestType)) {
            return new ResMsg(-1, SERVER_ERROR);
        } else {
            Map map = new HashMap();
            map.put("msg", exception.toString());
            return new ModelAndView("error/500", map);
        }
    }

}
