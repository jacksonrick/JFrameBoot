package com.jf.system.exception;

import com.jf.system.conf.LogManager;

/**
 * 异常类
 * Created by xujunfei on 2017/8/1.
 */
public class SysException extends RuntimeException {

    public SysException(String message) {
        super(message);
        LogManager.error(message);
    }

    public SysException(String message, Class cls) {
        super(message);
        LogManager.error(message, cls);
    }

    public SysException(String message, Throwable cause) {
        super(message, cause);
    }

    public SysException() {
        LogManager.error("System Error!");
    }

}
