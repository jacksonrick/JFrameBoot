package com.jf.database.model;

import com.jf.database.model.custom.BaseVo;

import java.io.Serializable;
import java.util.Arrays;

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

	/** 排序 */
	private Integer modSort;

	private String ids;
	private Integer[] flags;
	private Integer roleId;
	private Integer adminId;

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

	public Integer getModSort() {
		return modSort;
	}

	public void setModSort(Integer modSort) {
		this.modSort = modSort;
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

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	@Override
	public String toString() {
		return "Module{" +
				"id=" + id +
				", parentId=" + parentId +
				", modName='" + modName + '\'' +
				", modPath='" + modPath + '\'' +
				", modIcon='" + modIcon + '\'' +
				", modFlag=" + modFlag +
				", modSort=" + modSort +
				", ids='" + ids + '\'' +
				", flags=" + Arrays.toString(flags) +
				", roleId=" + roleId +
				", adminId=" + adminId +
				'}';
	}
}
