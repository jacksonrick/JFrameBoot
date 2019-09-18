package com.jf.exception;

import com.jf.annotation.Except;

/**
 * app token异常
 * Created by xujunfei on 2018/5/24.
 */
@Except(error = false, stack = false)
public class AppTokenException extends RuntimeException {

    public AppTokenException(String message) {
        super(message);
    }

    public AppTokenException(String message, Throwable cause) {
        super(message, cause);
    }

}
