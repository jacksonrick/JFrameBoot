package com.alipay.api.response;

import com.alipay.api.internal.mapping.ApiField;

import com.alipay.api.AlipayResponse;

/**
 * ALIPAY API: alipay.marketing.cdp.advertise.create response.
 * 
 * @author auto create
 * @since 1.0, 2017-04-01 15:29:02
 */
public class AlipayMarketingCdpAdvertiseCreateResponse extends AlipayResponse {

	private static final long serialVersionUID = 7798374653724435712L;

	/** 
	 * 创建广告唯一标识
	 */
	@ApiField("ad_id")
	private String adId;

	public void setAdId(String adId) {
		this.adId = adId;
	}
	public String getAdId( ) {
		return this.adId;
	}

}
