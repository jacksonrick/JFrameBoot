package com.jf.system.exception;

import com.jf.system.annotation.UnStack;

/**
 * Front未登录
 * Created by xujunfei on 2018/5/24.
 */
@UnStack
public class NoLoginException extends RuntimeException {

    public NoLoginException(String message) {
        super(message);
    }

}
