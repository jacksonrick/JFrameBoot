package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 停车缴费代扣接口API
 *
 * @author auto create
 * @since 1.0, 2016-12-22 21:53:37
 */
public class AlipayEcoMycarParkingOrderPayModel extends AlipayObject {

	private static final long serialVersionUID = 2361512724961656631L;

	/**
	 * 车牌，需要进行停车缴费代扣的车辆牌照
	 */
	@ApiField("car_number")
	private String carNumber;

	/**
	 * ISV停车场ID，由ISV定义的停车场标识，系统唯一，parking_id和out_parking_id不能同时为空
	 */
	@ApiField("out_parking_id")
	private String outParkingId;

	/**
	 * 支付宝合作商户网站唯一订单号
	 */
	@ApiField("out_trade_no")
	private String outTradeNo;

	/**
	 * 支付宝停车平台ID，由支付宝定义的该停车场标识，系统唯一, parking_id和out_parking_id不能同时为空
	 */
	@ApiField("parking_id")
	private String parkingId;

	/**
	 * 卖家支付宝用户号
卖家支付宝账号对应的支付宝唯一用户号。
以2088开头的纯16位数。与seller_logon_id不能同时为空
	 */
	@ApiField("seller_id")
	private String sellerId;

	/**
	 * 卖家支付宝账号，可以为email或者手机号。
如果seller_id不为空，则以seller_id的值作为卖家账号，忽略本参数。
	 */
	@ApiField("seller_logon_id")
	private String sellerLogonId;

	/**
	 * 订单标题，描述订单用途
	 */
	@ApiField("subject")
	private String subject;

	/**
	 * 订单金额，精确到小数点后两位
	 */
	@ApiField("total_fee")
	private String totalFee;

	public String getCarNumber() {
		return this.carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getOutParkingId() {
		return this.outParkingId;
	}
	public void setOutParkingId(String outParkingId) {
		this.outParkingId = outParkingId;
	}

	public String getOutTradeNo() {
		return this.outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getParkingId() {
		return this.parkingId;
	}
	public void setParkingId(String parkingId) {
		this.parkingId = parkingId;
	}

	public String getSellerId() {
		return this.sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerLogonId() {
		return this.sellerLogonId;
	}
	public void setSellerLogonId(String sellerLogonId) {
		this.sellerLogonId = sellerLogonId;
	}

	public String getSubject() {
		return this.subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTotalFee() {
		return this.totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

}
