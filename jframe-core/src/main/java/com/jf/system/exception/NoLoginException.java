package com.jf.system.exception;

import com.jf.annotation.Except;

/**
 * 未登录
 * Created by xujunfei on 2018/5/24.
 */
@Except(error = false, stack = false)
public class NoLoginException extends RuntimeException {

    public NoLoginException(String message) {
        super(message);
    }

}
