package com.jf.metrics;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-11-12
 * Time: 14:01
 */
@Measurement(name = "cpuinfo")
public class CPUInfo {

    @Column(name = "time")
    private String time;
    @Column(name = "device", tag = true)
    private String device;
    @Column(name = "value")
    private Integer value;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
