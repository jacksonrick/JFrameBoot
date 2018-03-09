package com.jf.system.conf;

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
    private static String serverId = "";

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

}
