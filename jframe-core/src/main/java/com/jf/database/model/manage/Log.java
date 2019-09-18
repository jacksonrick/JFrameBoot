package com.jf.database.model.manage;


import com.jf.database.model.custom.BaseVo;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志表
 * @date 2016年11月04日 下午 14:47:51
 * @author jfxu
 */
public class Log extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**  */
	private Integer id;

	/** 操作人 */
	private String operator;

	/** 备注 */
	private String remark;

	/** ip */
	private String ip;

	/** 参数 */
	private String params;

	/** 创建时间 */
	private Date createTime;

	public Log() {
	}

	public Log(Integer id) {
		super();
		this.id = id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Log{" +
				"id=" + id +
				", operator='" + operator + '\'' +
				", remark='" + remark + '\'' +
				", ip='" + ip + '\'' +
				", params='" + params + '\'' +
				", createTime=" + createTime +
				'}';
	}
}
