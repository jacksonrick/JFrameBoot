package com.alipay.api.response;

import com.alipay.api.internal.mapping.ApiField;

import com.alipay.api.AlipayResponse;

/**
 * ALIPAY API: alipay.eco.mycar.parking.parkinglotinfo.create response.
 * 
 * @author auto create
 * @since 1.0, 2016-12-22 15:57:47
 */
public class AlipayEcoMycarParkingParkinglotinfoCreateResponse extends AlipayResponse {

	private static final long serialVersionUID = 5441885713452747641L;

	/** 
	 * 支付宝返回停车场id。成功不为空，失败返回空
	 */
	@ApiField("parking_id")
	private String parkingId;

	public void setParkingId(String parkingId) {
		this.parkingId = parkingId;
	}
	public String getParkingId( ) {
		return this.parkingId;
	}

}
