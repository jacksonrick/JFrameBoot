package com.jf.exception;

import com.jf.annotation.Except;

/**
 * 无权限访问(Admin)
 * Created by xujunfei on 2018/5/24.
 */
@Except(error = false, stack = false)
public class NotAllowException extends RuntimeException {

    public NotAllowException(String message) {
        super(message);
    }

}
