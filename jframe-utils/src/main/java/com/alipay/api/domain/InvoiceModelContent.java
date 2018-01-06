package com.alipay.api.domain;

import java.util.List;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;
import com.alipay.api.internal.mapping.ApiListField;

/**
 * 发票详情模型
 *
 * @author auto create
 * @since 1.0, 2016-11-29 14:46:37
 */
public class InvoiceModelContent extends AlipayObject {

	private static final long serialVersionUID = 4668762885911652643L;

	/**
	 * key=value，每组键值对以回车分割
	 */
	@ApiField("extend_fields")
	private String extendFields;

	/**
	 * 下载的发票文件类型
可选值：
pdf（发票原文件）
jpg（发票原文件缩略图）
	 */
	@ApiField("file_download_type")
	private String fileDownloadType;

	/**
	 * 文件下载地址，当同步发票tax_type=PLAIN时，必传；
此处的链接请务必传入可下载PDF的链接
	 */
	@ApiField("file_download_url")
	private String fileDownloadUrl;

	/**
	 * 发票金额，大于0且精确到小数点两位，以元为单位
需要传入税价合计金额
	 */
	@ApiField("invoice_amount")
	private String invoiceAmount;

	/**
	 * 发票代码，国税局生成的唯一值，不可为空串
	 */
	@ApiField("invoice_code")
	private String invoiceCode;

	/**
	 * 发票内容项
	 */
	@ApiListField("invoice_content")
	@ApiField("invoice_item_content")
	private List<InvoiceItemContent> invoiceContent;

	/**
	 * 发票日期，用户填写，目前精确到日
	 */
	@ApiField("invoice_date")
	private String invoiceDate;

	/**
	 * 发票防伪码
	 */
	@ApiField("invoice_fake_code")
	private String invoiceFakeCode;

	/**
	 * 原始发票PDF文件流
	 */
	@ApiField("invoice_file_data")
	private String invoiceFileData;

	/**
	 * 发票原始文件jpg文件地址
	 */
	@ApiField("invoice_img_url")
	private String invoiceImgUrl;

	/**
	 * 发票号码，国税局生成的唯一号码，不可为空串；
使用时请注意，invoice_no+invoice_code唯一，不能重复
	 */
	@ApiField("invoice_no")
	private String invoiceNo;

	/**
	 * 发票开具操作人
	 */
	@ApiField("invoice_operator")
	private String invoiceOperator;

	/**
	 * 发票title
	 */
	@ApiField("invoice_title")
	private InvoiceTitleModel invoiceTitle;

	/**
	 * 发票类型，按照可选值只传入英文部分，该字段严格要求大小写
可选值:
blue（蓝票）
red（红票）
	 */
	@ApiField("invoice_type")
	private String invoiceType;

	/**
	 * 仅用于同步红票，原始蓝票发票代码，同步红票时必传
	 */
	@ApiField("original_blue_invoice_code")
	private String originalBlueInvoiceCode;

	/**
	 * 仅用于同步红票，原始蓝票发票号码，同步红票时必传
	 */
	@ApiField("original_blue_invoice_no")
	private String originalBlueInvoiceNo;

	/**
	 * 商户交易流水号，不可为空串;
传入红票时请注意，此字段的值要和蓝票保持一致
	 */
	@ApiField("out_biz_no")
	private String outBizNo;

	/**
	 * 商户唯一开票申请业务流水号，同一个isv下不能重复
	 */
	@ApiField("out_invoice_id")
	private String outInvoiceId;

	/**
	 * 开票单位地址
	 */
	@ApiField("register_address")
	private String registerAddress;

	/**
	 * 开票单位开户行账号
	 */
	@ApiField("register_bank_account")
	private String registerBankAccount;

	/**
	 * 开票单位开户行名称
	 */
	@ApiField("register_bank_name")
	private String registerBankName;

	/**
	 * 开票单位
	 */
	@ApiField("register_name")
	private String registerName;

	/**
	 * 纳税人识别号，不可为空串
	 */
	@ApiField("register_no")
	private String registerNo;

	/**
	 * 开票人电话，支持座机和手机两种格式
	 */
	@ApiField("register_phone_no")
	private String registerPhoneNo;

	/**
	 * 价税合计
	 */
	@ApiField("sum_amount")
	private String sumAmount;

	/**
	 * 税额
	 */
	@ApiField("tax_amount")
	private String taxAmount;

	/**
	 * 税种
可选值：
PLAIN（普票的情况）
SPECIAL（专票的情况）
	 */
	@ApiField("tax_type")
	private String taxType;

	/**
	 * 支付宝用户id,当同步的是蓝票时，必传。红票时不需传。
	 */
	@ApiField("user_id")
	private String userId;

	public String getExtendFields() {
		return this.extendFields;
	}
	public void setExtendFields(String extendFields) {
		this.extendFields = extendFields;
	}

	public String getFileDownloadType() {
		return this.fileDownloadType;
	}
	public void setFileDownloadType(String fileDownloadType) {
		this.fileDownloadType = fileDownloadType;
	}

	public String getFileDownloadUrl() {
		return this.fileDownloadUrl;
	}
	public void setFileDownloadUrl(String fileDownloadUrl) {
		this.fileDownloadUrl = fileDownloadUrl;
	}

	public String getInvoiceAmount() {
		return this.invoiceAmount;
	}
	public void setInvoiceAmount(String invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getInvoiceCode() {
		return this.invoiceCode;
	}
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public List<InvoiceItemContent> getInvoiceContent() {
		return this.invoiceContent;
	}
	public void setInvoiceContent(List<InvoiceItemContent> invoiceContent) {
		this.invoiceContent = invoiceContent;
	}

	public String getInvoiceDate() {
		return this.invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceFakeCode() {
		return this.invoiceFakeCode;
	}
	public void setInvoiceFakeCode(String invoiceFakeCode) {
		this.invoiceFakeCode = invoiceFakeCode;
	}

	public String getInvoiceFileData() {
		return this.invoiceFileData;
	}
	public void setInvoiceFileData(String invoiceFileData) {
		this.invoiceFileData = invoiceFileData;
	}

	public String getInvoiceImgUrl() {
		return this.invoiceImgUrl;
	}
	public void setInvoiceImgUrl(String invoiceImgUrl) {
		this.invoiceImgUrl = invoiceImgUrl;
	}

	public String getInvoiceNo() {
		return this.invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceOperator() {
		return this.invoiceOperator;
	}
	public void setInvoiceOperator(String invoiceOperator) {
		this.invoiceOperator = invoiceOperator;
	}

	public InvoiceTitleModel getInvoiceTitle() {
		return this.invoiceTitle;
	}
	public void setInvoiceTitle(InvoiceTitleModel invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getInvoiceType() {
		return this.invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getOriginalBlueInvoiceCode() {
		return this.originalBlueInvoiceCode;
	}
	public void setOriginalBlueInvoiceCode(String originalBlueInvoiceCode) {
		this.originalBlueInvoiceCode = originalBlueInvoiceCode;
	}

	public String getOriginalBlueInvoiceNo() {
		return this.originalBlueInvoiceNo;
	}
	public void setOriginalBlueInvoiceNo(String originalBlueInvoiceNo) {
		this.originalBlueInvoiceNo = originalBlueInvoiceNo;
	}

	public String getOutBizNo() {
		return this.outBizNo;
	}
	public void setOutBizNo(String outBizNo) {
		this.outBizNo = outBizNo;
	}

	public String getOutInvoiceId() {
		return this.outInvoiceId;
	}
	public void setOutInvoiceId(String outInvoiceId) {
		this.outInvoiceId = outInvoiceId;
	}

	public String getRegisterAddress() {
		return this.registerAddress;
	}
	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}

	public String getRegisterBankAccount() {
		return this.registerBankAccount;
	}
	public void setRegisterBankAccount(String registerBankAccount) {
		this.registerBankAccount = registerBankAccount;
	}

	public String getRegisterBankName() {
		return this.registerBankName;
	}
	public void setRegisterBankName(String registerBankName) {
		this.registerBankName = registerBankName;
	}

	public String getRegisterName() {
		return this.registerName;
	}
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public String getRegisterNo() {
		return this.registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getRegisterPhoneNo() {
		return this.registerPhoneNo;
	}
	public void setRegisterPhoneNo(String registerPhoneNo) {
		this.registerPhoneNo = registerPhoneNo;
	}

	public String getSumAmount() {
		return this.sumAmount;
	}
	public void setSumAmount(String sumAmount) {
		this.sumAmount = sumAmount;
	}

	public String getTaxAmount() {
		return this.taxAmount;
	}
	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getTaxType() {
		return this.taxType;
	}
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
