package com.jf.system.exception;

/**
 * 异常类
 * Created by xujunfei on 2017/8/1.
 */
public class UserException extends RuntimeException {

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserException() {
    }

}
