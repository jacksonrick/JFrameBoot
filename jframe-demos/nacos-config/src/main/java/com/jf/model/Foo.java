package com.jf.model;

/**
 * Created with IntelliJ IDEA.
 * Description: JSON配置
 * User: xujunfei
 * Date: 2019-03-04
 * Time: 16:35
 */
public class Foo {

    /**
     * 格式：JSON
     * {"a":9,"b":"xxx"}
     */

    private Integer a;

    private String b;

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "Foo{" +
                "a=" + a +
                ", b='" + b + '\'' +
                '}';
    }
}
