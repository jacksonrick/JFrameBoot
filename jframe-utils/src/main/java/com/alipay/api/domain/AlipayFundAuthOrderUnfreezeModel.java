package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 预授权资金解冻接口
 *
 * @author auto create
 * @since 1.0, 2016-11-29 15:55:43
 */
public class AlipayFundAuthOrderUnfreezeModel extends AlipayObject {

	private static final long serialVersionUID = 1668982625497899779L;

	/**
	 * 本次操作解冻的金额，单位为：元（人民币），精确到小数点后两位，取值范围：[0.01,100000000.00]
	 */
	@ApiField("amount")
	private String amount;

	/**
	 * 支付宝资金授权订单号
	 */
	@ApiField("auth_no")
	private String authNo;

	/**
	 * 商户本次资金操作的请求流水号，同一商户每次不同的资金操作请求，商户请求流水号不能重复
	 */
	@ApiField("out_request_no")
	private String outRequestNo;

	/**
	 * 商户对本次解冻操作的附言描述，长度不超过100个字母或50个汉字
	 */
	@ApiField("remark")
	private String remark;

	public String getAmount() {
		return this.amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAuthNo() {
		return this.authNo;
	}
	public void setAuthNo(String authNo) {
		this.authNo = authNo;
	}

	public String getOutRequestNo() {
		return this.outRequestNo;
	}
	public void setOutRequestNo(String outRequestNo) {
		this.outRequestNo = outRequestNo;
	}

	public String getRemark() {
		return this.remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
