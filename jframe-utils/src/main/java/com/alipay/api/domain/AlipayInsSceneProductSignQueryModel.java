package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 保险代扣协议签约查询
 *
 * @author auto create
 * @since 1.0, 2016-12-29 17:49:07
 */
public class AlipayInsSceneProductSignQueryModel extends AlipayObject {

	private static final long serialVersionUID = 2288324647959318946L;

	/**
	 * 产品编码
	 */
	@ApiField("prod_code")
	private String prodCode;

	/**
	 * 支付宝会员ID
	 */
	@ApiField("user_id")
	private String userId;

	public String getProdCode() {
		return this.prodCode;
	}
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
