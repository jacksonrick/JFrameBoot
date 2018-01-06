package com.jf.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统消息
 * @date 2017年09月11日 上午 11:05:15
 * @author jfxu
 */
public class Msg implements Serializable {

	private static final long serialVersionUID = 1L;

	/**  */
	private Long id;

	/** 管理ID,10000 */
	private Long adminId;

	/** 内容 */
	private String content;

	/**  */
	private Date createTime;

	public Msg() {
	}

	public Msg(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAdminId() {
		return this.adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
