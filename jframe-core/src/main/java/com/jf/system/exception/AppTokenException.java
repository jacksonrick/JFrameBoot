package com.jf.system.exception;

import com.jf.annotation.UnStack;

/**
 * app token异常
 * Created by xujunfei on 2018/5/24.
 */
@UnStack
public class AppTokenException extends RuntimeException {

    public AppTokenException(String message) {
        super(message);
    }

}
