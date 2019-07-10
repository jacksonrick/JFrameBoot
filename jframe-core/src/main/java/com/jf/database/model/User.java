package com.jf.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jf.database.model.custom.BaseVo;
import com.jf.database.model.custom.Extend;
import com.jf.system.handler.valid.EmptyPattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.TreeMap;

/**
 * 用户【仅用于测试】
 *
 * @date 2016年12月21日 上午 11:20:07
 * @author jfxu
 */
public class User extends BaseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 昵称 */
    @NotEmpty(message = "昵称不能为空")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "昵称格式错误，只能是英文和数字")
    @Length(min = 2, max = 16, message = "昵称在2-16个字符")
    private String nickname;

    /** 手机号 */
    @EmptyPattern(regexp = "^1[3|4|5|7|8][0-9]\\d{8}$", message = "手机号格式错误")
    private String phone;

    /** 邮箱 */
    @EmptyPattern(regexp = "\\w+((-w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+", message = "邮箱格式错误")
    private String email;

    /** 密码 */
    @EmptyPattern(regexp = "^[A-Za-z0-9]+$", message = "密码格式错误，只能是英文和数字")
    @JsonIgnore
    private String password;

    /** 头像 */
    private String avatar;

    /** 账户余额 */
    private Double money;

    /** 注册时间 */
    private Date createTime;

    /** 最近登陆时间 */
    private Date lastLoginTime;

    /** 真实姓名 */
    private String realname;

    /** 身份证号码 */
    @EmptyPattern(regexp = "^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$",message = "身份证格式不正确")
    private String idcard;

    /** 性别 1-男 0-女 */
    private Boolean gender;

    /** 住址 */
    private String address;

    /** 出生日期 */
    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    // @JsonFormat(pattern = "yyyy-MM-dd")
    // @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String birthday;

    /** 是否删除 1-是 0-否(默认) */
    private Boolean deleted;

    /** 是否锁定登陆 1-是 0-否(默认) */
    private Boolean locked;

    /** 密码错误次数 */
    private Integer wrongPwd;

    /** APP登陆token */
    private String token;

    // 拓展字段 Mybatis typeHandler属性
    private Extend extend;
    private TreeMap params;
    private String[] arr;

    public User() {
    }

    public User(Long id) {
        super();
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
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

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Integer getWrongPwd() {
        return wrongPwd;
    }

    public void setWrongPwd(Integer wrongPwd) {
        this.wrongPwd = wrongPwd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Extend getExtend() {
        return extend;
    }

    public void setExtend(Extend extend) {
        this.extend = extend;
    }

    public TreeMap getParams() {
        return params;
    }

    public void setParams(TreeMap params) {
        this.params = params;
    }

    public String[] getArr() {
        return arr;
    }

    public void setArr(String[] arr) {
        this.arr = arr;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", money=" + money +
                ", createTime=" + createTime +
                ", lastLoginTime=" + lastLoginTime +
                ", realname='" + realname + '\'' +
                ", idcard='" + idcard + '\'' +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                ", birthday='" + birthday + '\'' +
                ", deleted=" + deleted +
                ", locked=" + locked +
                ", wrongPwd=" + wrongPwd +
                ", token=" + token +
                ", extend=" + extend +
                ", params=" + params +
                ", arr=" + Arrays.toString(arr) +
                '}';
    }


    public static final class Builder {
        private User user;

        private Builder() {
            user = new User();
        }

        public static Builder anUser() {
            return new Builder();
        }

        public Builder id(Long id) {
            user.setId(id);
            return this;
        }

        public Builder nickname(String nickname) {
            user.setNickname(nickname);
            return this;
        }

        public Builder phone(String phone) {
            user.setPhone(phone);
            return this;
        }

        public Builder email(String email) {
            user.setEmail(email);
            return this;
        }

        public Builder password(String password) {
            user.setPassword(password);
            return this;
        }

        public Builder avatar(String avatar) {
            user.setAvatar(avatar);
            return this;
        }

        public Builder money(Double money) {
            user.setMoney(money);
            return this;
        }

        public Builder createTime(Date createTime) {
            user.setCreateTime(createTime);
            return this;
        }

        public Builder lastLoginTime(Date lastLoginTime) {
            user.setLastLoginTime(lastLoginTime);
            return this;
        }

        public Builder realname(String realname) {
            user.setRealname(realname);
            return this;
        }

        public Builder idcard(String idcard) {
            user.setIdcard(idcard);
            return this;
        }

        public Builder gender(Boolean gender) {
            user.setGender(gender);
            return this;
        }

        public Builder address(String address) {
            user.setAddress(address);
            return this;
        }

        public Builder birthday(String birthday) {
            user.setBirthday(birthday);
            return this;
        }

        public Builder deleted(Boolean deleted) {
            user.setDeleted(deleted);
            return this;
        }

        public Builder locked(Boolean locked) {
            user.setLocked(locked);
            return this;
        }

        public Builder wrongPwd(Integer wrongPwd) {
            user.setWrongPwd(wrongPwd);
            return this;
        }

        public User build() {
            return user;
        }
    }
}



