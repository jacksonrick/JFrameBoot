package com.jf.system.exception;

import com.jf.system.annotation.UnStack;

/**
 * app异常
 * Created by xujunfei on 2018/5/24.
 */
@UnStack
public class AppException extends RuntimeException {

    public AppException(String message) {
        super(message);
    }

}
