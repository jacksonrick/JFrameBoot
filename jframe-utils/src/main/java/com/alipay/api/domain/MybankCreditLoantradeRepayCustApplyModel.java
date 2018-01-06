package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 客户主动还款申请
 *
 * @author auto create
 * @since 1.0, 2017-03-14 15:16:20
 */
public class MybankCreditLoantradeRepayCustApplyModel extends AlipayObject {

	private static final long serialVersionUID = 1261551353734488412L;

	/**
	 * 申请还款费用（提前还款费除外）
	 */
	@ApiField("apply_repay_fee")
	private String applyRepayFee;

	/**
	 * 申请还款利息
	 */
	@ApiField("apply_repay_int")
	private String applyRepayInt;

	/**
	 * 提前还款费
	 */
	@ApiField("apply_repay_pre_fee")
	private String applyRepayPreFee;

	/**
	 * 申请还款本金
	 */
	@ApiField("apply_repay_prin")
	private String applyRepayPrin;

	/**
	 * 扩展字段
	 */
	@ApiField("ext_data")
	private String extData;

	/**
	 * 银行参与者id，是在网商银行创建会员后生成的id，网商银行会员的唯一标识
	 */
	@ApiField("ip_id")
	private String ipId;

	/**
	 * 银行参与者角色id，是在网商银行创建会员后生成的角色id，网商银行会员角色的唯一标识
	 */
	@ApiField("ip_role_id")
	private String ipRoleId;

	/**
	 * 贷款合约编号
	 */
	@ApiField("loan_ar_no")
	private String loanArNo;

	/**
	 * 请求流水号，用于幂等控制.以"ipRoleId_"开头
	 */
	@ApiField("request_id")
	private String requestId;

	public String getApplyRepayFee() {
		return this.applyRepayFee;
	}
	public void setApplyRepayFee(String applyRepayFee) {
		this.applyRepayFee = applyRepayFee;
	}

	public String getApplyRepayInt() {
		return this.applyRepayInt;
	}
	public void setApplyRepayInt(String applyRepayInt) {
		this.applyRepayInt = applyRepayInt;
	}

	public String getApplyRepayPreFee() {
		return this.applyRepayPreFee;
	}
	public void setApplyRepayPreFee(String applyRepayPreFee) {
		this.applyRepayPreFee = applyRepayPreFee;
	}

	public String getApplyRepayPrin() {
		return this.applyRepayPrin;
	}
	public void setApplyRepayPrin(String applyRepayPrin) {
		this.applyRepayPrin = applyRepayPrin;
	}

	public String getExtData() {
		return this.extData;
	}
	public void setExtData(String extData) {
		this.extData = extData;
	}

	public String getIpId() {
		return this.ipId;
	}
	public void setIpId(String ipId) {
		this.ipId = ipId;
	}

	public String getIpRoleId() {
		return this.ipRoleId;
	}
	public void setIpRoleId(String ipRoleId) {
		this.ipRoleId = ipRoleId;
	}

	public String getLoanArNo() {
		return this.loanArNo;
	}
	public void setLoanArNo(String loanArNo) {
		this.loanArNo = loanArNo;
	}

	public String getRequestId() {
		return this.requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

}
