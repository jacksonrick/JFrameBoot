package com.jf.system;

import com.jf.system.conf.SysConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * LogManager
 * logback & slf4j
 */
@Component
public class LogManager {

    @Resource
    private SysConfig config;

    private final static Logger log = LoggerFactory.getLogger(LogManager.class);
    private static LogManager manager;

    @PostConstruct
    public void init() {
        manager = this;
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
     * 访问信息
     *
     * @param ip
     * @param extra
     * @param action
     * @param params
     */
    public static void visit(String ip, String extra, String action, String params) {
        log.info("【Server ID：" + manager.config.getServerId() + "】" + "【Target IP：" + ip + "】【" + extra + "：" + action + "】【Params：" + params + "】");
    }

}
