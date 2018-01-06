package com.alipay.api.response;

import com.alipay.api.internal.mapping.ApiField;

import com.alipay.api.AlipayResponse;

/**
 * ALIPAY API: alipay.offline.marketing.voucher.create response.
 * 
 * @author auto create
 * @since 1.0, 2016-06-23 17:38:07
 */
public class AlipayOfflineMarketingVoucherCreateResponse extends AlipayResponse {

	private static final long serialVersionUID = 5386247833761757492L;

	/** 
	 * 活动id，过渡方案的兼容字段
	 */
	@ApiField("activity_id")
	private String activityId;

	/** 
	 * 券模板id
	 */
	@ApiField("voucher_id")
	private String voucherId;

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getActivityId( ) {
		return this.activityId;
	}

	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}
	public String getVoucherId( ) {
		return this.voucherId;
	}

}
