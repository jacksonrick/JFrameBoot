package com.jf.database.model.manage;

import com.jf.database.model.custom.BaseVo;
import com.jf.system.handler.valid.EmptyPattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Date;

/**
 * 后台管理员
 * @date 2016年11月04日 下午 14:47:51
 * @author jfxu
 */
public class Admin extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** id */
	private Integer id;

	/** 用户组id */
	private Integer roleId;

	/** 用户名 */
    @NotEmpty(message = "不能为空")
    @EmptyPattern(regexp = "^[A-Za-z0-9]+$", message = "用户名格式错误，只能是英文和数字")
    @Length(min = 2, max = 16, message = "用户名在2-16个字符")
	private String loginName;

    /** 登录密码 */
	@EmptyPattern(regexp = "^[A-Za-z0-9]+$", message = "密码格式错误，只能是英文和数字")
	private String password;

	/** 真实姓名 */
    @Length(max = 10, message = "姓名最多10个字符")
	private String realname;

	/** 手机号 */
    @EmptyPattern(regexp = "^1[3|4|5|7|8][0-9]\\d{8}$", message = "手机号格式错误")
	private String phone;

	/** 权限 */
    private String rights;

	/** 账号创建日期 */
	private Date createTime;

	/** 更新时间 */
	private Date updateTime;

	/** 上次登录时间 */
	private Date lastLoginTime;

	/** 上次登录ip */
	private String lastLoginIp;

    /** 是否删除 1-是 0-否 */
    private Boolean deleted;

	/** 用户组 */
	private Role role;

	public Admin() {
	}

	public Admin(Integer id) {
		super();
		this.id = id;
	}

	/**
	 * @param loginName
	 */
	public Admin(String loginName) {
		this.setLoginName(loginName);
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRights() {
		return rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "Admin{" +
				"id=" + id +
				", roleId=" + roleId +
				", loginName='" + loginName + '\'' +
				", password='" + password + '\'' +
				", realname='" + realname + '\'' +
				", phone='" + phone + '\'' +
				", rights='" + rights + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				", lastLoginTime=" + lastLoginTime +
				", lastLoginIp='" + lastLoginIp + '\'' +
				", deleted=" + deleted +
				", role=" + role +
				'}';
	}
}
