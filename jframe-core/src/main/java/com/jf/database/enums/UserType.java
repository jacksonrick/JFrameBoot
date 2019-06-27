package com.jf.database.enums;

/**
 * 用户类型
 *
 * @author rick
 * @date 2016-8-20
 */
public enum UserType {

    /**
     * 1-普通用户
     */
    Normal(1, "普通用户"),
    /**
     * 2-高级用户
     */
    Vip(2, "高级用户");

    private Integer value;

    private String desc;

    UserType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer value() {
        return value;
    }

    public String desc() {
        return desc;
    }

    public static String get(Integer type) {
        for (UserType u : UserType.values()) {
            if (u.value().equals(type)) {
                return u.desc();
            }
        }
        return "--";
    }
}
