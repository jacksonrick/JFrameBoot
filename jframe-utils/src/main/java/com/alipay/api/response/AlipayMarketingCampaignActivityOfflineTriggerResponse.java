package com.alipay.api.response;

import com.alipay.api.internal.mapping.ApiField;

import com.alipay.api.AlipayResponse;

/**
 * ALIPAY API: alipay.marketing.campaign.activity.offline.trigger response.
 * 
 * @author auto create
 * @since 1.0, 2017-04-14 19:07:28
 */
public class AlipayMarketingCampaignActivityOfflineTriggerResponse extends AlipayResponse {

	private static final long serialVersionUID = 2692521818848157416L;

	/** 
	 * 外部奖品ID
	 */
	@ApiField("out_prize_id")
	private String outPrizeId;

	public void setOutPrizeId(String outPrizeId) {
		this.outPrizeId = outPrizeId;
	}
	public String getOutPrizeId( ) {
		return this.outPrizeId;
	}

}
