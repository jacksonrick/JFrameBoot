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
	private String adminName;

	/** 真实姓名 */
    @Length(max = 10, message = "姓名最多10个字符")
	private String adminRealname;

	/** 登录密码 */
    @EmptyPattern(regexp = "^[A-Za-z0-9]+$", message = "密码格式错误，只能是英文和数字")
	private String adminPassword;

	/** 手机号 */
    @EmptyPattern(regexp = "^1[3|4|5|7|8][0-9]\\d{8}$", message = "手机号格式错误")
	private String adminPhone;

	/** 权限 */
    private String adminRights;

	/** 账号创建日期 */
	private Date adminCreateTime;

	/** 上次登录时间 */
	private Date adminLoginTime;

	/** 上次登录ip */
	private String adminLoginIp;

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
	 * @param adminName
	 */
	public Admin(String adminName) {
		this.setAdminName(adminName);
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

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminName() {
		return this.adminName;
	}

	public void setAdminRealname(String adminRealname) {
		this.adminRealname = adminRealname;
	}

	public String getAdminRealname() {
		return this.adminRealname;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getAdminPassword() {
		return this.adminPassword;
	}

	public void setAdminPhone(String adminPhone) {
		this.adminPhone = adminPhone;
	}

	public String getAdminPhone() {
		return this.adminPhone;
	}

	public void setAdminRights(String adminRights) {
		this.adminRights = adminRights;
	}

	public String getAdminRights() {
		return adminRights;
	}

	public Date getAdminCreateTime() {
        return adminCreateTime;
    }

    public void setAdminCreateTime(Date adminCreateTime) {
        this.adminCreateTime = adminCreateTime;
    }

    public void setAdminLoginTime(Date adminLoginTime) {
		this.adminLoginTime = adminLoginTime;
	}

	public Date getAdminLoginTime() {
		return this.adminLoginTime;
	}

	public void setAdminLoginIp(String adminLoginIp) {
		this.adminLoginIp = adminLoginIp;
	}

	public String getAdminLoginIp() {
		return this.adminLoginIp;
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
				", adminName='" + adminName + '\'' +
				", adminRealname='" + adminRealname + '\'' +
				", adminPassword='" + adminPassword + '\'' +
				", adminPhone='" + adminPhone + '\'' +
				", adminRights='" + adminRights + '\'' +
				", adminCreateTime=" + adminCreateTime +
				", adminLoginTime=" + adminLoginTime +
				", adminLoginIp='" + adminLoginIp + '\'' +
				", deleted=" + deleted +
				", role=" + role +
				'}';
	}
}
