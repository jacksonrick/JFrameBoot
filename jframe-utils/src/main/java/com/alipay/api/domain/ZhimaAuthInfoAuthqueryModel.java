package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 查询是否授权的接口
 *
 * @author auto create
 * @since 1.0, 2016-09-21 19:49:58
 */
public class ZhimaAuthInfoAuthqueryModel extends AlipayObject {

	private static final long serialVersionUID = 5834936772567911132L;

	/**
	 * json格式的内容,包含userId,userId为支付宝用户id,用户授权后商户可以拿到这个支付宝userId
	 */
	@ApiField("identity_param")
	private String identityParam;

	public String getIdentityParam() {
		return this.identityParam;
	}
	public void setIdentityParam(String identityParam) {
		this.identityParam = identityParam;
	}

}
