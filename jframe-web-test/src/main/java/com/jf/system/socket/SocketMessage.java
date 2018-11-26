package com.jf.system.socket;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description: WebSocket接收对象
 * User: xujunfei
 * Date: 2018-01-09
 * Time: 15:49
 */
public class SocketMessage implements Serializable {

    private Integer id;
    /** 当前用户 */
    private String username;
    /** 目标用户 */
    private String target;
    /** 信息内容 */
    private String message;
    /** 发送时间 */
    private Date date;

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
                "id=" + id +
                ", username='" + username + '\'' +
                ", target='" + target + '\'' +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }
}
