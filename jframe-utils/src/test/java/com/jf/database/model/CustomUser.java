package com.jf.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jf.json.StringArraySerialize;

import java.util.Date;

/**
 * Created by xujunfei on 2017/1/4.
 */
public class CustomUser {

    @JsonSerialize(using = ToStringSerializer.class)
    private Integer id;

    @JsonSerialize(using = StringArraySerialize.class)
    private Integer[] ids;

    private String name;

    private Integer age;

    private Boolean flag;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    private Date dates;

    private CustomRole role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }

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

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getDates() {
        return dates;
    }

    public void setDates(Date dates) {
        this.dates = dates;
    }

    public CustomRole getRole() {
        return role;
    }

    public void setRole(CustomRole role) {
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
