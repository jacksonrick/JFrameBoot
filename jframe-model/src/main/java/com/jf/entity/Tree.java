package com.jf.entity;

/**
 * 模块树
 * 
 * @author rick
 */
public class Tree {

	private Integer id;

	private Integer pId;

	private Boolean checked;

	private String name;

	public Tree() {
	}

	public Tree(Integer id, Integer pId, Boolean checked, String name) {
		super();
		this.id = id;
		this.pId = pId;
		this.checked = checked;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
