package com.jf.entity;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2020-06-23
 * Time: 11:03
 */
public class WorkerNodeEntity {

    private long id;
    private String hostname;
    private String port;
    private Date crtdate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Date getCrtdate() {
        return crtdate;
    }

    public void setCrtdate(Date crtdate) {
        this.crtdate = crtdate;
    }

    @Override
    public String toString() {
        return "WorkerNodeEntity{" +
                "id=" + id +
                ", hostname='" + hostname + '\'' +
                ", port='" + port + '\'' +
                ", crtdate='" + crtdate + '\'' +
                '}';
    }

}
