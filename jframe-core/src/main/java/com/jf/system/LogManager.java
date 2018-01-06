package com.jf.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LogManager
 * logback & slf4j
 */
public class LogManager {

    private final static Logger log = LoggerFactory.getLogger("syslog");

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
     * @param type
     * @param ip
     * @param action
     * @param params
     */
    public static void visit(String type, String ip, String action, String params) {
        /*log.info("########## " + type + " Interceptor ##########\r\n" +
                "\tTarget IP\t" + ip + "\r\n\tAction\t\t" + action + "\r\n\tParams\t\t" + params);*/
        log.info("【" + type + "】" + "【Target IP：" + ip + "】【Action：" + action + "】【Params：" + params + "】");
    }

}
