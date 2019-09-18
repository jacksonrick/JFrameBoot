package com.jf.database.model.manage;

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
	private String name;

	/** 访问action */
	private String action;

    /** 访问参数 */
    //private String param;

	/** 模块图标 */
	private String iconName;

	/** 1,2表示层级 | 3表示功能 */
	private Integer flag;

	/** 排序 */
	private Integer sort;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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
				", name='" + name + '\'' +
				", action='" + action + '\'' +
				", iconName='" + iconName + '\'' +
				", flag=" + flag +
				", sort=" + sort +
				", ids='" + ids + '\'' +
				", flags=" + Arrays.toString(flags) +
				", roleId=" + roleId +
				", adminId=" + adminId +
				'}';
	}
}
