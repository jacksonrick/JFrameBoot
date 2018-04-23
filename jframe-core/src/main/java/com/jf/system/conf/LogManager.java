package com.jf.system.conf;

import com.jf.system.Environment;
import com.jf.system.SystemUtil;
import com.jf.system.exception.SysException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * LogManager
 * logback & slf4j
 */
@Component
public class LogManager {

    @Resource
    private SysConfig config;

    private final static Logger log = LoggerFactory.getLogger(LogManager.class);
    private static String serverId = "NULL";

    @PostConstruct
    public void init() {
        serverId = config.getServerId();
    }

    /**
     * @param msg
     */
    public static void info(String msg) {
        log.info(msg);
    }

    /**
     * @param msg
     * @param cls
     */
    public static void info(String msg, Class cls) {
        LoggerFactory.getLogger(cls).info(msg);
    }

    /**
     * @param msg
     */
    public static void warn(String msg) {
        log.warn(msg);
    }

    /**
     * @param msg
     */
    public static void error(String msg) {
        log.error(msg);
    }

    /**
     * @param msg
     * @param cls
     */
    public static void error(String msg, Class cls) {
        LoggerFactory.getLogger(cls).error(msg);
    }

    /**
     * @param msg
     * @param e
     */
    public static void error(String msg, Exception e) {
        log.error(msg, e);
    }

    /**
     * 访问记录
     *
     * @param request
     */
    public static void visit(HttpServletRequest request) {
        if ("NULL".equals(serverId)) {
            throw new SysException("Server ID is not be initialled on LogManager");
        }
        // Action
        String path = request.getRequestURI();
        // IP
        String remote = request.getHeader("x-forwarded-for") == null ? request.getRemoteAddr() : request.getHeader("x-forwarded-for");
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
        StringBuilder sb = new StringBuilder("【Server ID：")
                .append(serverId).append("】").append("【Target IP：").append(remote).append("】【")
                .append(path).append("】【Params：").append(param).append("】");
        log.info(sb.toString());
    }

    /**
     * 启动检测
     *
     * @param appName
     */
    public static void startup(String appName) {
        StringBuffer sb = new StringBuffer(appName + " starting...");
        double[] space = new SystemUtil().getSpace();
        sb.append("\n操作系统：\t").append(Environment.getOsName())
                .append("\n系统版本：\t").append(Environment.getOsVersion())
                .append("\nIP地址：\t\t").append(Environment.getHostAddress())
                .append("\nJAVA版本：\t").append(Environment.getJavaVersion())
                .append("\nJAVAHOME：\t").append(Environment.getJavaHome())
                .append("\nJAVA类库：\t").append(Environment.getLibraryPath())
                .append("\n磁盘空间：\t").append("可用:").append(space[0]).append("GB, 已用:").append(space[1]).append("GB, 总计:").append(space[2]).append("GB")
                .append("\n内存：\t\t").append("可用:").append(space[3]).append("GB, 已用:").append(space[4]).append("GB, 总计:").append(space[5]).append("GB");

        log.info(sb.toString());

        // 最低硬盘空间和内存
        double minSpace = 5;
        double minMem = 0.5;
        if (space[0] < minSpace || space[3] < minMem) {
            LogManager.error("磁盘或内存空间不足【minSpace:" + minSpace + "GB, minMem:" + minMem + "GB】");
            System.exit(0);
        }
    }

}
