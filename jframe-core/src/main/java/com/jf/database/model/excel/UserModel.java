package com.jf.database.model.excel;

import com.jf.annotation.excel.Excel;
import com.jf.annotation.excel.FieldType;
import com.jf.annotation.excel.Fields;
import com.jf.annotation.excel.TypeValue;
import com.jf.json.DoubleSerialize;
import com.jf.poi.render.StateRender;

/**
 * Created with IntelliJ IDEA.
 * Description: mybatis -> model -> excel
 * User: xujunfei
 * Date: 2018-06-27
 * Time: 11:15
 */
@Excel(name = "用户列表")
public class UserModel {

    @Fields(value = "姓名")
    private String nickname;
    @Fields(value = "手机号")
    private String phone;
    @Fields(value = "邮箱")
    private String email;
    @Fields(value = "余额")
    private String money;
    @Fields(value = "注册时间")
    private String create_time;
    @Fields(value = "最后登录时间")
    private String last_login_time;
    @Fields(value = "真实姓名")
    private String realname;
    @Fields(value = "身份证号")
    private String idcard;
    @Fields(value = "性别", type = FieldType.ENUM, typeValues = {
            @TypeValue(name = "男", value = "1"),
            @TypeValue(name = "女", value = "0")
    })
    private String gender;
    @Fields(value = "住址")
    private String address;
    @Fields(value = "生日")
    private String birthday;
    @Fields(value = "状态", type = FieldType.ENUM, render = StateRender.class)
    private String deleted;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }
}
