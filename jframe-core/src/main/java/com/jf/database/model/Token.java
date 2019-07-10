package com.jf.database.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description: App Token
 * User: xujunfei
 * Date: 2019-04-03
 * Time: 15:13
 */
public class Token {

    private String uid;
    private String token;
    private Date created;
    private Date expired;

    public Token() {
    }

    public Token(String uid, String token, Date expired) {
        this.uid = uid;
        this.token = token;
        this.expired = expired;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    @Override
    public String toString() {
        return "Token{" +
                "uid='" + uid + '\'' +
                ", token='" + token + '\'' +
                ", created=" + created +
                ", expired=" + expired +
                '}';
    }
}
