package com.jf.exception;

import com.jf.annotation.Except;

/**
 * api token异常
 * Created by xujunfei on 2018/5/24.
 */
@Except(error = false, stack = false)
public class ApiTokenException extends RuntimeException {

    public ApiTokenException(String message) {
        super(message);
    }

    public ApiTokenException(String message, Throwable cause) {
        super(message, cause);
    }

}
