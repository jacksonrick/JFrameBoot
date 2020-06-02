package com.jf.metrics;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.TimeColumn;

import java.time.Instant;
import java.time.ZoneId;

public class Label {

    @TimeColumn
    @Column(name = "time")
    private Long time;

    @Column(name = "x")
    private double x;
    @Column(name = "y")
    private double y;
    @Column(name = "z")
    private double z;

    @Column(name = "v")
    private String v;

    @Column(name = "m", tag = true)
    private String m;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return "Label{" +
                ", time=" + time +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", v='" + v +
                ", m=" + m +
                '}';
    }

}
