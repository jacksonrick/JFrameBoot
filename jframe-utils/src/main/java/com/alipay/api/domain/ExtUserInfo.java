package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 外部指定买家
 *
 * @author auto create
 * @since 1.0, 2017-06-06 18:11:24
 */
public class ExtUserInfo extends AlipayObject {

	private static final long serialVersionUID = 7873767152262595374L;

	/**
	 * 证件号
	 */
	@ApiField("cert_no")
	private String certNo;

	/**
	 * 身份证：IDENTITY_CARD、护照：PASSPORT、军官证：OFFICER_CARD、士兵证：SOLDIER_CARD、户口本：HOKOU等。如有其它类型需要支持，请与蚂蚁金服工作人员联系。
	 */
	@ApiField("cert_type")
	private String certType;

	/**
	 * 是否强制校验付款人身份信息[统一接口定义，T:强制校验，F：不强制
	 */
	@ApiField("fix_buyer")
	private String fixBuyer;

	/**
	 * 手机号
	 */
	@ApiField("mobile")
	private String mobile;

	/**
	 * 姓名
	 */
	@ApiField("name")
	private String name;

	public String getCertNo() {
		return this.certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getCertType() {
		return this.certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getFixBuyer() {
		return this.fixBuyer;
	}
	public void setFixBuyer(String fixBuyer) {
		this.fixBuyer = fixBuyer;
	}

	public String getMobile() {
		return this.mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
