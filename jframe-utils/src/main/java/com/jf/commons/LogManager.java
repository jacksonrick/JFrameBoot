package com.jf.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LogManager
 * logback & slf4j
 */
public class LogManager {

    private final static Logger log = LoggerFactory.getLogger(LogManager.class);

    /**
     * @param msg
     */
    public static void info(String msg) {
        log.info(msg);
    }

    /**
     * @param msg
     * @param throwable
     */
    public static void info(String msg, Throwable throwable) {
        log.info(msg, throwable);
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
     * @param throwable
     */
    public static void error(String msg, Throwable throwable) {
        log.error(msg, throwable);
    }

    /**
     * 启动检测
     *
     * @param appName
     */
    public static void startup(String appName) {
        StringBuffer sb = new StringBuffer(appName + " starting...");
        double[] space = SystemUtil.getSpace();
        sb.append("\n操作系统：\t").append(Environment.getOsName())
                .append("\n系统版本：\t").append(Environment.getOsVersion())
                .append("\nIP地址：\t").append(Environment.getHostAddress())
                .append("\nJAVA版本：\t").append(Environment.getJavaVersion())
                .append("\nJAVAHOME：\t").append(Environment.getJavaHome())
                .append("\nJAVA类库：\t").append(Environment.getLibraryPath())
                .append("\n磁盘空间：\t").append("可用:").append(space[0]).append("GB, 已用:").append(space[1]).append("GB, 总计:").append(space[2]).append("GB")
                .append("\n内存：\t").append("可用:").append(space[3]).append("GB, 已用:").append(space[4]).append("GB, 总计:").append(space[5]).append("GB");

        log.info(sb.toString());

        // 最低硬盘空间和内存
        double minSpace = 5;
        double minMem = 0.3;
        if (space[0] < minSpace || space[3] < minMem) {
            LogManager.warn("磁盘或内存空间不足【minSpace:" + minSpace + "GB, minMem:" + minMem + "GB】");
            //System.exit(0);
        }
    }

}
