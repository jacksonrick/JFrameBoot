package com.jf.entity.enums;

/**
 * 状态
 * <p>已列举系统所需大部分的状态值，注意数据库中的状态需要严格按照此枚举类型</p>
 *
 * @author rick
 * @date 2016-8-20
 */
public enum State {

    /** 0-未删除 */
    NotDelete(0, "未删除"),
    /** 1-删除 */
    Delete(1, "删除"),

    /** 0-成功 */
    Success(0, "成功"),
    /** 1-失败 */
    Fail(1, "失败"),

    /** 0-否 */
    No(0, "否"),
    /** 1-是 */
    Yes(1, "是"),

    /** 0-未读 */
    Unread(0, "未读"),
    /** 1-已读 */
    Read(1, "已读"),

    /** 0-未支付 */
    NotPay(0, "未支付"),
    /** 1-已支付 */
    Pay(1, "已支付");

	private Integer value;

	private String desc;

	State(Integer value, String desc) {
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
