package com.jf.system.exception;

import com.jf.annotation.Except;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-07-05
 * Time: 18:17
 */
@Except(stack = false)
public class RestException extends RuntimeException {

    private Integer code;

    public RestException() {
        this.code = -1;
    }

    public RestException(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
