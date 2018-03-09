package com.jf.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-03-06
 * Time: 11:39
 */
public class Log {

    private final static Logger log = LoggerFactory.getLogger(Log.class);

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

}
