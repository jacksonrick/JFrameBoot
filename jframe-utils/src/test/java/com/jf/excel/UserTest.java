package com.jf.excel;

import com.jf.annotation.excel.Excel;
import com.jf.annotation.excel.FieldType;
import com.jf.annotation.excel.Fields;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-03-12
 * Time: 15:47
 */
@Excel(name = "测试表格")
public class UserTest {

    @Fields(value = "ID")
    private Integer A;
    @Fields(value = "BB")
    private String B;
    @Fields(value = "CC")
    private String C;
    @Fields(value = "DD")
    private String D;
    @Fields(value = "EE")
    private String E;
    @Fields(value = "FF")
    private String F;
    @Fields(value = "GG", type = FieldType.BOOLEAN)
    private String G;

    public UserTest(Integer a) {
        A = a;
    }

    public Integer getA() {
        return A;
    }

    public void setA(Integer a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getE() {
        return E;
    }

    public void setE(String e) {
        E = e;
    }

    public String getF() {
        return F;
    }

    public void setF(String f) {
        F = f;
    }

    public String getG() {
        return G;
    }

    public void setG(String g) {
        G = g;
    }

}
