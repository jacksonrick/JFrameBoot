package com.jf.excel;

import com.jf.annotation.excel.Excel;
import com.jf.annotation.excel.Fields;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description: mybatis -> model -> excel
 * User: xujunfei
 * Date: 2018-06-27
 * Time: 11:15
 */
@Excel(name = "人员明细列表")
public class PersonModel {

    @Fields(value = "姓名")
    private String username;
    @Fields(value = "手机号")
    private String mobile;
    @Fields(value = "邮箱")
    private String email;
    @Fields(value = "住址")
    private String address;
    @Fields(value = "年龄")
    private Integer age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "PersonModel{" +
                "username='" + username + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
