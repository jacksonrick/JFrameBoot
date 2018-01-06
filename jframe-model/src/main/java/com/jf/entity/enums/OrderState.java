package com.jf.entity.enums;

/**
 * Created by xujunfei on 2017/6/29.
 */
public enum OrderState {

    /** -1-订单取消 */
    S11(-1, "订单取消"),

    /** 0-待付款 */
    S0(0, "待付款"),

    /** 2-已支付(待发货) */
    S2(2, "已支付(待发货)"),

    /** 3-已发货(待收货) */
    S3(3, "已发货(待收货)"),

    /** 4-已收货(待评价) */
    S4(4, "已收货(待评价)"),

    /** 5-已评价 */
    S5(5, "已评价");

    private Integer value;

    private String desc;

    OrderState(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer value() {
        return value;
    }

    public String desc() {
        return desc;
    }

}
