package com.jf.system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 获取路径
 * Created by xujunfei on 2017/2/15.
 */
@Component
public class PathUtil {

    // 静态文件目录
    public static String static_path;

    // 日志文件目录
    public static String log_path;

    @Value("${system.static_path}")
    public void setStatic_path(String static_path) {
        PathUtil.static_path = static_path;
    }

    @Value("${system.log_path}")
    public void setLog_path(String log_path) {
        PathUtil.log_path = log_path;
    }

    //不适用于SpringBoot
    /*public static String BASEPATH = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/") + "/";

    static {
        if (BASEPATH.endsWith("//")) {
            BASEPATH = BASEPATH.substring(0, BASEPATH.length() - 1);
        }
    }*/
}
