package com.jf.model;

import com.jf.model.custom.BaseVo;

import java.io.Serializable;

/**
 * 省市区街道
 * @date 2017年05月09日 上午 11:06:55
 * @author jfxu
 */
public class Address extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**  */
	private Integer id;

    /**  */
    private String name;

	/**  */
	private Integer parent;

	/** 层级 1-4 */
	private Integer level;

    public Address() {
    }

    public Address(Integer id) {
        this.id = id;
    }

    public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getLevel() {
		return this.level;
	}

}
