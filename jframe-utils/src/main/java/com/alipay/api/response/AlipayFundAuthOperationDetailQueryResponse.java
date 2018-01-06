package com.alipay.api.response;

import java.util.Date;
import com.alipay.api.internal.mapping.ApiField;

import com.alipay.api.AlipayResponse;

/**
 * ALIPAY API: alipay.fund.auth.operation.detail.query response.
 * 
 * @author auto create
 * @since 1.0, 2017-03-13 11:27:36
 */
public class AlipayFundAuthOperationDetailQueryResponse extends AlipayResponse {

	private static final long serialVersionUID = 8573129376655453363L;

	/** 
	 * 该笔资金操作流水opertion_id对应的操作金额，单位为：元（人民币）
	 */
	@ApiField("amount")
	private String amount;

	/** 
	 * 支付宝资金授权订单号
	 */
	@ApiField("auth_no")
	private String authNo;

	/** 
	 * 商户请求创建预授权订单时传入的扩展参数，仅返回商户自定义的扩展信息（merchantExt）
	 */
	@ApiField("extra_param")
	private String extraParam;

	/** 
	 * 资金授权单据操作流水创建时间，
格式：YYYY-MM-DD HH:MM:SS
	 */
	@ApiField("gmt_create")
	private Date gmtCreate;

	/** 
	 * 支付宝账务处理成功时间，
格式：YYYY-MM-DD HH:MM:SS
	 */
	@ApiField("gmt_trans")
	private Date gmtTrans;

	/** 
	 * 支付宝资金操作流水号
	 */
	@ApiField("operation_id")
	private String operationId;

	/** 
	 * 支付宝资金操作类型，
目前支持： 
FREEZE：冻结
UNFREEZE：解冻
PAY：支付
	 */
	@ApiField("operation_type")
	private String operationType;

	/** 
	 * 业务订单的简单描述，如商品名称等
	 */
	@ApiField("order_title")
	private String orderTitle;

	/** 
	 * 商户的授权资金订单号
	 */
	@ApiField("out_order_no")
	private String outOrderNo;

	/** 
	 * 商户资金操作的请求流水号
	 */
	@ApiField("out_request_no")
	private String outRequestNo;

	/** 
	 * 付款方支付宝账号（Email或手机号），仅作展示使用，默认会加“*”号处理
	 */
	@ApiField("payer_logon_id")
	private String payerLogonId;

	/** 
	 * 付款方支付宝账号对应的支付宝唯一用户号，以2088开头的16位纯数字组成
	 */
	@ApiField("payer_user_id")
	private String payerUserId;

	/** 
	 * 商户对本次操作的附言描述，长度不超过100个字母或50个汉字
	 */
	@ApiField("remark")
	private String remark;

	/** 
	 * 订单总共剩余的冻结金额，单位为：元（人民币）
	 */
	@ApiField("rest_amount")
	private String restAmount;

	/** 
	 * 资金操作流水的状态，
目前支持： 
INIT：初始
SUCCESS：成功
CLOSED：关闭
	 */
	@ApiField("status")
	private String status;

	/** 
	 * 订单累计的冻结金额，单位为：元（人民币）
	 */
	@ApiField("total_freeze_amount")
	private String totalFreezeAmount;

	/** 
	 * 订单累计用于支付的金额，单位为：元（人民币）
	 */
	@ApiField("total_pay_amount")
	private String totalPayAmount;

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

	public void setExtraParam(String extraParam) {
		this.extraParam = extraParam;
	}
	public String getExtraParam( ) {
		return this.extraParam;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtCreate( ) {
		return this.gmtCreate;
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

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getOperationType( ) {
		return this.operationType;
	}

	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}
	public String getOrderTitle( ) {
		return this.orderTitle;
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

	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRemark( ) {
		return this.remark;
	}

	public void setRestAmount(String restAmount) {
		this.restAmount = restAmount;
	}
	public String getRestAmount( ) {
		return this.restAmount;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus( ) {
		return this.status;
	}

	public void setTotalFreezeAmount(String totalFreezeAmount) {
		this.totalFreezeAmount = totalFreezeAmount;
	}
	public String getTotalFreezeAmount( ) {
		return this.totalFreezeAmount;
	}

	public void setTotalPayAmount(String totalPayAmount) {
		this.totalPayAmount = totalPayAmount;
	}
	public String getTotalPayAmount( ) {
		return this.totalPayAmount;
	}

}
