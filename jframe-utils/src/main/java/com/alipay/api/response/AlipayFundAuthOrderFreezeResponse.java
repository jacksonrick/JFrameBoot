package com.alipay.api.response;

import java.util.Date;
import com.alipay.api.internal.mapping.ApiField;

import com.alipay.api.AlipayResponse;

/**
 * ALIPAY API: alipay.fund.auth.order.freeze response.
 * 
 * @author auto create
 * @since 1.0, 2017-06-01 16:10:22
 */
public class AlipayFundAuthOrderFreezeResponse extends AlipayResponse {

	private static final long serialVersionUID = 8546686148395499142L;

	/** 
	 * 本次操作冻结的金额，单位为：元（人民币），精确到小数点后两位
	 */
	@ApiField("amount")
	private String amount;

	/** 
	 * 支付宝的资金授权订单号
	 */
	@ApiField("auth_no")
	private String authNo;

	/** 
	 * 资金授权成功时间
格式：YYYY-MM-DD HH:MM:SS
	 */
	@ApiField("gmt_trans")
	private Date gmtTrans;

	/** 
	 * 支付宝的资金操作流水号
	 */
	@ApiField("operation_id")
	private String operationId;

	/** 
	 * 商户的授权资金订单号
	 */
	@ApiField("out_order_no")
	private String outOrderNo;

	/** 
	 * 商户本次资金操作的请求流水号
	 */
	@ApiField("out_request_no")
	private String outRequestNo;

	/** 
	 * 收款方支付宝账号（Email或手机号）
	 */
	@ApiField("payer_logon_id")
	private String payerLogonId;

	/** 
	 * 付款方支付宝用户号
	 */
	@ApiField("payer_user_id")
	private String payerUserId;

	/** 
	 * 资金预授权明细的状态
目前支持：  INIT：初始
SUCCESS: 成功
CLOSED：关闭
	 */
	@ApiField("status")
	private String status;

	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAmount( ) {
		return this.amount;
	}

	public void setAuthNo(String authNo) {
		this.authNo = authNo;
	}
	public String getAuthNo( ) {
		return this.authNo;
	}

	public void setGmtTrans(Date gmtTrans) {
		this.gmtTrans = gmtTrans;
	}
	public Date getGmtTrans( ) {
		return this.gmtTrans;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
	public String getOperationId( ) {
		return this.operationId;
	}

	public void setOutOrderNo(String outOrderNo) {
		this.outOrderNo = outOrderNo;
	}
	public String getOutOrderNo( ) {
		return this.outOrderNo;
	}

	public void setOutRequestNo(String outRequestNo) {
		this.outRequestNo = outRequestNo;
	}
	public String getOutRequestNo( ) {
		return this.outRequestNo;
	}

	public void setPayerLogonId(String payerLogonId) {
		this.payerLogonId = payerLogonId;
	}
	public String getPayerLogonId( ) {
		return this.payerLogonId;
	}

	public void setPayerUserId(String payerUserId) {
		this.payerUserId = payerUserId;
	}
	public String getPayerUserId( ) {
		return this.payerUserId;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus( ) {
		return this.status;
	}

}
