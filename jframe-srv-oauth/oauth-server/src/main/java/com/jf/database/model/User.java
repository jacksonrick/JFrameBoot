package com.jf.database.model;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-10-31
 * Time: 15:36
 */
public class User {

    private String username;
    private String password;
    private String roles;

    public User() {
    }

    public User(User user) {
        this.username = user.username;
        this.password = user.password;
        this.roles = user.roles;
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
