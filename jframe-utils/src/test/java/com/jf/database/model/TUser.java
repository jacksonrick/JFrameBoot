package com.jf.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by xujunfei on 2017/1/4.
 */
public class TUser {

    private String name;

    private Integer age;

    private Boolean flag;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    private Date dates;

    private TRole role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Date getTime() {
        return time;
    }

    public void setTimes(Date time) {
        this.time = time;
    }

    public Date getDates() {
        return dates;
    }

    public void setDates(Date dates) {
        this.dates = dates;
    }

    public TRole getRole() {
        return role;
    }

    public void setRole(TRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "TUser{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", flag=" + flag +
                ", birth=" + birth +
                ", time=" + time +
                ", dates=" + dates +
                ", role=" + role +
                '}';
    }
}
