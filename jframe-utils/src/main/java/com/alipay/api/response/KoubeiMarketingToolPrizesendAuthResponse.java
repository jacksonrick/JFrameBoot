package com.alipay.api.response;

import com.alipay.api.internal.mapping.ApiField;

import com.alipay.api.AlipayResponse;

/**
 * ALIPAY API: koubei.marketing.tool.prizesend.auth response.
 * 
 * @author auto create
 * @since 1.0, 2016-10-09 11:45:06
 */
public class KoubeiMarketingToolPrizesendAuthResponse extends AlipayResponse {

	private static final long serialVersionUID = 6648759778236781538L;

	/** 
	 * 发奖token，用于校验商户是否有权限给制定用户发奖
	 */
	@ApiField("token")
	private String token;

	public void setToken(String token) {
		this.token = token;
	}
	public String getToken( ) {
		return this.token;
	}

}
