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
	private String logUser;

	/** 备注 */
	private String logRemark;

	/** ip */
	private String logIp;

	/** 参数 */
	private String logParams;

	/** 创建时间 */
	private Date logCreateTime;

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

	public void setLogUser(String logUser) {
		this.logUser = logUser;
	}

	public String getLogUser() {
		return this.logUser;
	}

	public void setLogRemark(String logRemark) {
		this.logRemark = logRemark;
	}

	public String getLogRemark() {
		return this.logRemark;
	}

	public void setLogIp(String logIp) {
		this.logIp = logIp;
	}

	public String getLogIp() {
		return this.logIp;
	}

	public void setLogParams(String logParams) {
		this.logParams = logParams;
	}

	public String getLogParams() {
		return this.logParams;
	}

	public void setLogCreateTime(Date logCreateTime) {
		this.logCreateTime = logCreateTime;
	}

	public Date getLogCreateTime() {
		return this.logCreateTime;
	}

	@Override
	public String toString() {
		return "Log{" +
				"id=" + id +
				", logUser='" + logUser + '\'' +
				", logRemark='" + logRemark + '\'' +
				", logIp='" + logIp + '\'' +
				", logParams='" + logParams + '\'' +
				", logCreateTime=" + logCreateTime +
				'}';
	}
}
