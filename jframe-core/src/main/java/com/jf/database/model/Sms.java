package com.jf.database.model;

import com.jf.database.model.custom.BaseVo;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信记录
 * @date 2017年06月14日 上午 09:32:08
 * @author jfxu
 */
public class Sms extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**  */
	private Long id;

	/**  */
	private String phone;

	/**  */
	private String code;

	/**  */
	private String source;

	/**  */
	private Date createTime;

	/**  */
	private Date updateTime;

	/**  */
	private Boolean valided;

	public Sms() {
	}

	public Sms(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean getValided() {
		return valided;
	}

	public void setValided(Boolean valided) {
		this.valided = valided;
	}
}
