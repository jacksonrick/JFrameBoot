package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 芝麻认证查询
 *
 * @author auto create
 * @since 1.0, 2017-04-24 09:46:32
 */
public class ZhimaCustomerCertificationQueryModel extends AlipayObject {

	private static final long serialVersionUID = 7772313766454358998L;

	/**
	 * 一次认证的唯一标识,在商户调用认证初始化接口的时候获取
	 */
	@ApiField("biz_no")
	private String bizNo;

	public String getBizNo() {
		return this.bizNo;
	}
	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

}
