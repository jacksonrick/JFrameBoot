package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 口碑订单详情
 *
 * @author auto create
 * @since 1.0, 2016-11-21 14:07:02
 */
public class KoubeiTradeOrderQueryModel extends AlipayObject {

	private static final long serialVersionUID = 8373142339845682982L;

	/**
	 * 口碑订单号
	 */
	@ApiField("order_no")
	private String orderNo;

	public String getOrderNo() {
		return this.orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
