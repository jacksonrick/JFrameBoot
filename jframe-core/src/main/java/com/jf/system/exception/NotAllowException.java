package com.jf.system.exception;

import com.jf.system.annotation.UnStack;

/**
 * 无权限访问(Admin)
 * Created by xujunfei on 2018/5/24.
 */
@UnStack
public class NotAllowException extends RuntimeException {

    public NotAllowException(String message) {
        super(message);
    }

}
