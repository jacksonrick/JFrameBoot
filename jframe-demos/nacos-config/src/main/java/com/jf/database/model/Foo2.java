package com.jf.database.model;

import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description: 键值对配置
 * User: xujunfei
 * Date: 2019-03-04
 * Time: 17:08
 */
@Component
@NacosConfigurationProperties(dataId = "user2", groupId = "com.jf", autoRefreshed = true)
public class Foo2 {

    private Integer a;
    private Integer b;
    private Integer c;

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }

    public Integer getC() {
        return c;
    }

    public void setC(Integer c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "Foo2{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                '}';
    }
}
