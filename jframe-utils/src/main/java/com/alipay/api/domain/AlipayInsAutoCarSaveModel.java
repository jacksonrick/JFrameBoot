package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 蚂蚁乐驾车主车辆保存服务
 *
 * @author auto create
 * @since 1.0, 2016-10-26 18:05:18
 */
public class AlipayInsAutoCarSaveModel extends AlipayObject {

	private static final long serialVersionUID = 7651647925246988769L;

	/**
	 * 车牌号
	 */
	@ApiField("car_no")
	private String carNo;

	/**
	 * 用户ID,车主会员ID
	 */
	@ApiField("user_id")
	private String userId;

	public String getCarNo() {
		return this.carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
