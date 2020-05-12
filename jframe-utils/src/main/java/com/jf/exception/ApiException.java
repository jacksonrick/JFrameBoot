package com.jf.exception;

/**
 * api异常
 * Created by xujunfei on 2018/5/24.
 */
public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

}
