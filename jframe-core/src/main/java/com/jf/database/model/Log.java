package com.jf.database.model;


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
	private Long id;

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

	public Log(Long id) {
		super();
		this.id = id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
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

}
