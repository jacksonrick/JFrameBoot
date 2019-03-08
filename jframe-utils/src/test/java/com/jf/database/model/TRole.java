package com.jf.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-06-29
 * Time: 11:59
 */
public class TRole {

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "TRole{" +
                "name='" + name + '\'' +
                ", time=" + time +
                '}';
    }
}
