package com.jf.exception;

import com.jf.commons.LogManager;

/**
 * http超时
 * Created by xujunfei on 2017/8/1.
 */
public class HttpTimeoutException extends RuntimeException {

    public HttpTimeoutException(String message) {
        super(message);
    }

    public HttpTimeoutException(String message, Class cls) {
        super(message);
    }

    public HttpTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpTimeoutException() {
        LogManager.error("Http请求超时");
    }

}
