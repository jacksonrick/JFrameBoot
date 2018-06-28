package com.jf.database.model;

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
	private Integer id;

	/** 管理ID,10000 */
	private Integer adminId;

	/** 内容 */
	private String content;

	/**  */
	private Date createTime;

	public Msg() {
	}

	public Msg(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAdminId() {
		return this.adminId;
	}

	public void setAdminId(Integer adminId) {
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

	@Override
	public String toString() {
		return "Msg{" +
				"id=" + id +
				", adminId=" + adminId +
				", content='" + content + '\'' +
				", createTime=" + createTime +
				'}';
	}
}
