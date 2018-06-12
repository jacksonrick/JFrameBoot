package com.jf;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-06-12
 * Time: 09:32
 */
public class TestUser {

    private Integer id;
    private String username;
    private Integer age;
    private Date createTime;
    private Boolean enable;

    public TestUser id(Integer id) {
        this.id = id;
        return this;
    }

    public TestUser username(String username) {
        this.username = username;
        return this;
    }

    public TestUser age(Integer age) {
        this.age = age;
        return this;
    }

    public TestUser createTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public TestUser enable(Boolean enable) {
        this.enable = enable;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "TestUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", createTime=" + createTime +
                ", enable=" + enable +
                '}';
    }
}
