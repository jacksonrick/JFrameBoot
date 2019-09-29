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
    private String id;

    /** 名称 */
    private String username;

    public User() {
    }

    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public User(String id) {
        super();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}



