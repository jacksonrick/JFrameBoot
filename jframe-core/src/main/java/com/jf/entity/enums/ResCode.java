package com.jf.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * Description: 枚举返回码
 * User: xujunfei
 * Date: 2018-05-28
 * Time: 10:38
 */
public enum ResCode {

    // 成功|失败
    SUCCESS(0, "成功"),
    FAIL(1, "失败"),

    // 增删改 成功=0
    INSERT_SUCCESS(0, "新增成功"),
    INSERT_FAIL(11, "新增失败"),
    UPDATE_SUCCESS(0, "更新成功"),
    UPDATE_FAIL(13, "更新失败"),
    DELETE_SUCCESS(0, "删除成功"),
    DELETE_FAIL(15, "删除失败"),
    OPERATE_SUCCESS(0, "操作成功"),
    OPERATE_FAIL(17, "操作失败"),

    // 业务 CODE_2?
    CODE_21(21, "未查询到数据"),
    CODE_22(22, "不合法的ID"),
    CODE_23(23, "不合法的状态"),
    CODE_24(24, "参数错误"),

    // 系统 >9?
    NO_LOGIN(99, "未登录"),
    NOT_ALLOW(100, "拒绝访问"),
    TOKEN_EXP(101, "登录已过期，请重新登录"),
    APP_ERROR(102, "APP服务错误"),
    TIMEOUT(408, "请求超时"),
    REFUSE(403, "请求拒绝"),
    ERROR(500, "服务器开小差了，请稍后再试"),

    // http
    HTTP_OK(0, "OK"),
    HTTP_ERROR(1, "ERROR"),
    HTTP_TIMEOUT(2, "TIMEOUT");

    private Integer code;
    private String msg;

    public Integer code() {
        return code;
    }

    public String msg() {
        return msg;
    }

    ResCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
