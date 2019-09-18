package com.jf.database.model.manage;

import com.jf.database.model.custom.BaseVo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * 后台用户组
 * @date 2016年11月04日 下午 14:47:51
 * @author jfxu
 */
public class Role extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** id */
	private Integer id;

	/** 用户组名称 */
    @NotEmpty(message = "用户组名称不能为空")
    @Length(min = 2, max = 6, message = "用户组名称长度在2-6个字符之间")
	private String name;

	/** 权限 */
	private String rights;

	/** 是否可编辑 1-可编辑 0-不可编辑 */
	private Integer flag;

    /** 是否删除 1-是 0-否 */
    private Boolean deleted;

	public Role() {
	}

	public Role(Integer id) {
		super();
		this.id = id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRights() {
		return rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

	@Override
	public String toString() {
		return "Role{" +
				"id=" + id +
				", name='" + name + '\'' +
				", rights='" + rights + '\'' +
				", flag=" + flag +
				", deleted=" + deleted +
				'}';
	}
}
