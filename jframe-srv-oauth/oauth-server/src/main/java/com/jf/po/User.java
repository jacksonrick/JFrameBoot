package com.jf.po;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-10-31
 * Time: 15:36
 */
public class User {

    private String uuid;
    private String username;
    private String password;
    private String userType;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.uuid = UUID.randomUUID().toString();
        this.userType = "1";
    }

    public User(User user) {
        this.username = user.username;
        this.password = user.password;
        this.uuid = user.uuid;
        this.userType = user.userType;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
