package com.alipay.api.response;

import com.alipay.api.internal.mapping.ApiField;
import com.alipay.api.domain.UnfreezeOrderDetail;

import com.alipay.api.AlipayResponse;

/**
 * ALIPAY API: alipay.micropay.order.unfreeze response.
 * 
 * @author auto create
 * @since 1.0, 2016-06-06 17:54:23
 */
public class AlipayMicropayOrderUnfreezeResponse extends AlipayResponse {

	private static final long serialVersionUID = 8171229147698245461L;

	/** 
	 * 冻结订单详情结果
	 */
	@ApiField("unfreeze_order_detail")
	private UnfreezeOrderDetail unfreezeOrderDetail;

	public void setUnfreezeOrderDetail(UnfreezeOrderDetail unfreezeOrderDetail) {
		this.unfreezeOrderDetail = unfreezeOrderDetail;
	}
	public UnfreezeOrderDetail getUnfreezeOrderDetail( ) {
		return this.unfreezeOrderDetail;
	}

}
