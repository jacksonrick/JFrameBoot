package com.alipay.api.response;

import com.alipay.api.internal.mapping.ApiField;

import com.alipay.api.AlipayResponse;

/**
 * ALIPAY API: alipay.user.test response.
 * 
 * @author auto create
 * @since 1.0, 2016-01-14 17:47:44
 */
public class AlipayUserTestResponse extends AlipayResponse {

	private static final long serialVersionUID = 6885238421184437387L;

	/** 
	 * 返回值
	 */
	@ApiField("ret1")
	private String ret1;

	public void setRet1(String ret1) {
		this.ret1 = ret1;
	}
	public String getRet1( ) {
		return this.ret1;
	}

}
