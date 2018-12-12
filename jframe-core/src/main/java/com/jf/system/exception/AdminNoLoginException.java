package com.jf.system.exception;

import com.jf.annotation.UnStack;

/**
 * Admin未登录
 * Created by xujunfei on 2018/5/24.
 */
@UnStack
public class AdminNoLoginException extends RuntimeException {

    public AdminNoLoginException(String message) {
        super(message);
    }

}
