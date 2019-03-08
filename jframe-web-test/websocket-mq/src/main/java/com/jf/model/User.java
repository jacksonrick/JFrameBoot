package com.jf.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 *
 * @date 2016年12月21日 上午 11:20:07
 * @author jfxu
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    private Integer id;

    /** 昵称 */
    private String nickname;

    /** 手机号 */
    private String phone;

    /** 头像 */
    private String avatar;

    /** 账户余额 */
    private Double money;

    /** 注册时间 */
    private Date createTime;

    /** 是否删除 1-是 0-否(默认) */
    private Boolean deleted;

    public User() {
    }

    public User(Integer id) {
        super();
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                ", avatar='" + avatar + '\'' +
                ", money=" + money +
                ", createTime=" + createTime +
                ", deleted=" + deleted +
                '}';
    }
}



