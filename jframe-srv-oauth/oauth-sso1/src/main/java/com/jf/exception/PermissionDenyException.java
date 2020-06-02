package com.jf.exception;

public class PermissionDenyException extends RuntimeException {

    public PermissionDenyException(String message) {
        super(message);
    }

    public PermissionDenyException(String message, Throwable cause) {
        super(message, cause);
    }

}
