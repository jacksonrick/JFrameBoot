package com.alipay.api.response;

import com.alipay.api.internal.mapping.ApiField;

import com.alipay.api.AlipayResponse;

/**
 * ALIPAY API: alipay.pcredit.loan.refund.create response.
 * 
 * @author auto create
 * @since 1.0, 2016-08-25 10:32:15
 */
public class AlipayPcreditLoanRefundCreateResponse extends AlipayResponse {

	private static final long serialVersionUID = 1221963148499335886L;

	/** 
	 * 受理的还款申请单号
	 */
	@ApiField("loan_repay_no")
	private String loanRepayNo;

	public void setLoanRepayNo(String loanRepayNo) {
		this.loanRepayNo = loanRepayNo;
	}
	public String getLoanRepayNo( ) {
		return this.loanRepayNo;
	}

}
