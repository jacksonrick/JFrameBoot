package com.alipay.api.response;

import com.alipay.api.internal.mapping.ApiField;

import com.alipay.api.AlipayResponse;

/**
 * ALIPAY API: alipay.exsc.user.firstfundinpour.get response.
 * 
 * @author auto create
 * @since 1.0, 2017-04-07 16:23:40
 */
public class AlipayExscUserFirstfundinpourGetResponse extends AlipayResponse {

	private static final long serialVersionUID = 5168667535761185597L;

	/** 
	 * 返回结果的业务类型。首次资金流入业务类型 （first_fund_inpour）
	 */
	@ApiField("biz_type")
	private String bizType;

	/** 
	 * 业务处理结果
 true 无资金流入记录，本次是首次交易
 false 已有资金流入记录，本次不是首次交易
	 */
	@ApiField("success")
	private Boolean success;

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getBizType( ) {
		return this.bizType;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public Boolean getSuccess( ) {
		return this.success;
	}

}
