package com.jf.database.model;

import com.jf.database.model.custom.BaseVo;

import java.io.Serializable;

/**
 * 后台模块
 * @date 2016年11月04日 下午 14:47:51
 * @author jfxu
 */
public class Module extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** id */
	private Integer id;

	/** 父模块id 0表示一级分类 */
	private Integer parentId;

	/** 模块名 */
	private String modName;

	/** 访问action */
	private String modPath;

    /** 访问参数 */
    //private String modParam;

	/** 模块图标 */
	private String modIcon;

	/** 1,2表示层级 | 3表示功能 */
	private Integer modFlag;

	private String ids;
	private Integer[] flags;
	private Long roleId;
	private Long adminId;

	public Module() {
	}

	public Module(Integer id) {
		super();
		this.id = id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setModName(String modName) {
		this.modName = modName;
	}

	public String getModName() {
		return this.modName;
	}

	public void setModPath(String modPath) {
		this.modPath = modPath;
	}

	public String getModPath() {
		return this.modPath;
	}

	public void setModIcon(String modIcon) {
		this.modIcon = modIcon;
	}

	public String getModIcon() {
		return this.modIcon;
	}

	public void setModFlag(Integer modFlag) {
		this.modFlag = modFlag;
	}

	public Integer getModFlag() {
		return this.modFlag;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer[] getFlags() {
		return flags;
	}

	public void setFlags(Integer[] flags) {
		this.flags = flags;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}
}
