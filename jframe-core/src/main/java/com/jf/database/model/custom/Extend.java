package com.jf.database.model.custom;

/**
 * Created with IntelliJ IDEA.
 * Description: 拓展字段类
 * User: xujunfei
 * Date: 2018-06-19
 * Time: 09:38
 */
public class Extend {

    private String a;
    private String b;
    private String c;

    public String getA() {
        return a;
    }

    public void setA(String a) {
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
        return "Extend{" +
                "a='" + a + '\'' +
                ", b='" + b + '\'' +
                '}';
    }
}
