package com.jf.system.ws;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-01-09
 * Time: 15:49
 */
public class SocketMessage implements Serializable {

    private String username;

    private String target;

    private String message;

    private Date date;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SocketMessage{" +
                "username='" + username + '\'' +
                ", target='" + target + '\'' +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }
}
