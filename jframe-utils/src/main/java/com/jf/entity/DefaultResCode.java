package com.jf.entity;

/**
 * Created with IntelliJ IDEA.
 * Description: ResMsg Code
 * User: xujunfei
 * Date: 2019-06-27
 * Time: 14:00
 */
public enum DefaultResCode {

    // 成功固定code为0
    SUCCESS(0, "SUCCESS"),
    FAIL(1, "FAIL");

    private Integer code;
    private String msg;

    public Integer code() {
        return code;
    }

    public String msg() {
        return msg;
    }

    DefaultResCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
