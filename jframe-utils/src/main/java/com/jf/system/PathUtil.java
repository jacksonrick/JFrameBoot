package com.jf.system;

import com.jf.json.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by xujunfei on 2017/2/15.
 */
public class PathUtil {

    //不适用于SpringBoot
    /*public static String BASEPATH = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/") + "/";

    static {
        if (BASEPATH.endsWith("//")) {
            BASEPATH = BASEPATH.substring(0, BASEPATH.length() - 1);
        }
    }*/


    /**
     * 跳转-未登录
     *
     * @param url
     * @param response
     * @param request
     * @throws IOException
     */
    @Deprecated
    public static void nologin(String url, HttpServletResponse response, HttpServletRequest request) throws IOException {
        String requestType = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equalsIgnoreCase(requestType)) { // AJAX
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter pw = response.getWriter();
            pw.print(JSONUtils.toJSONString(new Result(99, "未登录", url)));
            pw.flush();
            pw.close();
        } else { // 页面请求
            response.sendRedirect(request.getContextPath() + url);
        }
    }

    /**
     * 跳转-拒绝
     *
     * @param url
     * @param response
     * @param request
     * @throws IOException
     */
    @Deprecated
    public static void refuse(String url, HttpServletResponse response, HttpServletRequest request) throws IOException {
        String requestType = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equalsIgnoreCase(requestType)) { // AJAX
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter pw = response.getWriter();
            pw.print(JSONUtils.toJSONString(new Result(100, "拒绝访问", url)));
            pw.flush();
            pw.close();
        } else { // 页面请求
            response.sendRedirect(request.getContextPath() + url);
        }
    }

    /**
     * APP
     *
     * @param code
     * @param msg
     * @param response
     * @throws IOException
     */
    @Deprecated
    public static void output(Integer code, String msg, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter pw = response.getWriter();
        pw.print(JSONUtils.toJSONString(new Result(code, msg)));
        pw.flush();
        pw.close();
    }
}
