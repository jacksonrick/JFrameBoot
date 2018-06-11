package com.jf.system.exception;

import com.jf.system.LogManager;

/**
 * 自定义系统异常
 * Created by xujunfei on 2017/8/1.
 */
public class SysException extends RuntimeException {

    public SysException(String message) {
        super(message);
    }

    public SysException(String message, Class cls) {
        super(message);
    }

    public SysException(String message, Throwable cause) {
        super(message, cause);
    }

    public SysException() {
        LogManager.error("System Error!");
    }

}
